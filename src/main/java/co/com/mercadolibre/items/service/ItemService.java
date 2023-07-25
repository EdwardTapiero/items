package co.com.mercadolibre.items.service;

import co.com.mercadolibre.items.model.model.ItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {
    Mono<ItemDTO> createItem(ItemDTO itemDTO);

    Flux<ItemDTO> getAllItems(int page, int size);

    Mono<ItemDTO> getItemById(String id);

    Mono<ItemDTO> updateItem(ItemDTO item);

    Mono<Void> deleteItem(String id);
}
