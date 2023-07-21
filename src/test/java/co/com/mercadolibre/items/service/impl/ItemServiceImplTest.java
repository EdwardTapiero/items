package co.com.mercadolibre.items.service.impl;

import co.com.mercadolibre.items.model.entity.Item;
import co.com.mercadolibre.items.model.model.ItemDTO;
import co.com.mercadolibre.items.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.ReactiveGeoOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ItemServiceImplTest {
    @Mock
    private ReactiveRedisTemplate redisTemplate;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ItemServiceImpl itemServiceImpl;
    @Mock
    private ReactiveValueOperations reactiveValueOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId("id");
        itemDTO.setName("name");
        itemDTO.setDescription("desc");
        itemDTO.setPrice(1.0);

        Item item = new Item();
        item.setId("id");

        when(modelMapper.map(any(ItemDTO.class), eq(Item.class))).thenReturn(item);
        when(itemRepository.save(any())).thenReturn(Mono.just(item));
        when(modelMapper.map(any(Item.class), eq(ItemDTO.class))).thenReturn(itemDTO);
        Mono<ItemDTO> result = itemServiceImpl.createItem(itemDTO);
        assertNotNull(result);
    }

    @Test
    void testGetAllItems() {
        Item data = new Item();
        when(itemRepository.retrieveAllItemsPaged(any())).thenReturn(Flux.just(data));
        when(modelMapper.map(any(), any())).thenReturn(new ItemDTO());
        Flux<Object> result = itemServiceImpl.getAllItems(1, 1);
        assertNotNull(result);
    }

    @Test
    void testGetItemById() {
        Item data = new Item();
        when(redisTemplate.opsForValue()).thenReturn(reactiveValueOperations);
        when(reactiveValueOperations.get(anyString())).thenReturn(Mono.just(new Object()));
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(data));
        when(modelMapper.map(any(), any())).thenReturn(new ItemDTO());
        Mono<Object> result = itemServiceImpl.getItemById("id");
        assertNotNull(result);
    }

    @Test
    void testUpdateItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId("id");
        itemDTO.setName("name");
        itemDTO.setDescription("desc");
        itemDTO.setPrice(1.0);

        Item item = new Item();
        item.setId("id");
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(item));
        when(modelMapper.map(any(ItemDTO.class), eq(Item.class))).thenReturn(item);
        when(modelMapper.map(any(Item.class), eq(ItemDTO.class))).thenReturn(itemDTO);
        Mono<ItemDTO> result = itemServiceImpl.updateItem(new ItemDTO("id", "name", "description", (double) 0));
        assertNotNull(result);
    }

    @Test
    void testDeleteItem() {
        when(redisTemplate.opsForValue()).thenReturn(reactiveValueOperations);
        when(reactiveValueOperations.delete(any())).thenReturn(Mono.just(new Object()));
        Mono<Void> result = itemServiceImpl.deleteItem("id");
        assertNull(result);
    }
}