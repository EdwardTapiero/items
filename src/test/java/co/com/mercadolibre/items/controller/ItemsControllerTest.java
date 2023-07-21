package co.com.mercadolibre.items.controller;

import co.com.mercadolibre.items.model.model.ItemDTO;
import co.com.mercadolibre.items.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ItemsControllerTest {
    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemsController itemsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateItem() {
        when(itemService.createItem(any())).thenReturn(null);

        ResponseEntity<Mono<ItemDTO>> result = itemsController.createItem(new ItemDTO("id", "name", "description", 1.0));
        assertNotNull(result);
    }

    @Test
    void testGetAllItems() {
        when(itemService.getAllItems(anyInt(), anyInt())).thenReturn(null);

        ResponseEntity<Flux<Object>> result = itemsController.getAllItems(0, 0);
        assertNotNull(result);
    }

    @Test
    void testGetItemById() {
        when(itemService.getItemById(anyString())).thenReturn(null);

        ResponseEntity<Mono<Object>> result = itemsController.getItemById("id");
        assertNotNull(result);
    }

    @Test
    void testUpdateItem() {
        when(itemService.updateItem(any())).thenReturn(null);

        ResponseEntity<Mono<ItemDTO>> result = itemsController.updateItem(new ItemDTO("id", "name", "description", 1.0));
        assertNotNull(result);
    }

    @Test
    void testDeleteItem() {
        when(itemService.deleteItem(anyString())).thenReturn(null);

        ResponseEntity<Mono<Void>> result = itemsController.deleteItem("id");
        assertNotNull(result);
    }
}