package co.com.mercadolibre.items.service.impl;

import co.com.mercadolibre.items.core.exception.ItemException;
import co.com.mercadolibre.items.model.entity.Item;
import co.com.mercadolibre.items.model.model.ItemDTO;
import co.com.mercadolibre.items.service.ItemService;
import co.com.mercadolibre.items.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
class ItemServiceImpl implements ItemService {

    private final ReactiveRedisTemplate redisTemplate;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public ItemServiceImpl(
            @Qualifier("reactiveRedisTemplate") ReactiveRedisTemplate redisTemplate, ItemRepository itemRepository,
            ModelMapper modelMapper) {
        this.redisTemplate = redisTemplate;
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<ItemDTO> createItem(ItemDTO itemDTO) {
        Item item = modelMapper.map(itemDTO, Item.class);

        return itemRepository.save(item)
                .map(savedItem -> modelMapper.map(savedItem, ItemDTO.class))
                .doOnSuccess(cache -> redisTemplate.opsForHash().put("item-cache", cache.getId(), cache));
    }

    @Override
    public Flux<ItemDTO> getAllItems(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        return itemRepository.retrieveAllItemsPaged(pageRequest)
                .map(item -> modelMapper.map(item, ItemDTO.class));
    }

    @Override
    public Mono<ItemDTO> getItemById(String id) {
        return redisTemplate.opsForValue().get(id)
                .switchIfEmpty(itemRepository.findById(id)
                        .map(updatedItem -> modelMapper.map(updatedItem, ItemDTO.class))
                        .flatMap(getItemDTOMonoFunction())
                        .switchIfEmpty(Mono.error(new ItemException(HttpStatus.BAD_REQUEST, "No information was found for the specified id"))));
    }

    private Function<ItemDTO, Mono<?>> getItemDTOMonoFunction() {
        return item -> redisTemplate.opsForValue().set(item.getId(), item)
                .thenReturn(item);
    }

    @Override
    public Mono<ItemDTO> updateItem(ItemDTO requestDTO) {
        return itemRepository.findById(requestDTO.getId())
                .flatMap(existingItem -> {
                    modelMapper.map(requestDTO, existingItem);
                    return itemRepository.save(existingItem);
                })
                .map(updatedItem -> modelMapper.map(updatedItem, ItemDTO.class))
                .doOnSuccess(itemCache -> redisTemplate.opsForValue().set(itemCache.getId(), itemCache));
    }

    @Override
    public Mono<Void> deleteItem(String id) {
        redisTemplate.opsForValue().delete(id);
        return itemRepository.deleteById(id);
    }
}
