package co.com.mercadolibre.items.controller;

import co.com.mercadolibre.items.model.model.ItemDTO;
import co.com.mercadolibre.items.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/items")
@Tag(name = "Items", description = "APIS para prueba técnica MeLi")
public class ItemsController {

    private final ItemService itemService;

    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(
            summary = "Creación de item",
            description = "Creación de item")
    @PostMapping
    public ResponseEntity<Mono<ItemDTO>> createItem(@Valid @RequestBody ItemDTO item) {
        return ResponseEntity.ok(itemService.createItem(item));
    }

    @Operation(
            summary = "Obtener todos los items",
            description = "Obtener todos los items, se genera por paginación")
    @GetMapping
    public ResponseEntity<Flux<Object>> getAllItems(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(itemService.getAllItems(page, size));
    }

    @Operation(
            summary = "Obtener item por id",
            description = "Obtener item por id")
    @GetMapping("/{id}")
    public ResponseEntity<Mono<Object>> getItemById(@PathVariable String id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @Operation(
            summary = "Guardar pqrd",
            description = "Guardar radicacion para PQRD")
    @PutMapping
    public ResponseEntity<Mono<ItemDTO>> updateItem(@RequestBody ItemDTO item) {
        return ResponseEntity.ok(itemService.updateItem(item));
    }

    @Operation(
            summary = "Guardar pqrd",
            description = "Guardar radicacion para PQRD")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteItem(@PathVariable String id) {
        return ResponseEntity.ok(itemService.deleteItem(id));
    }
}
