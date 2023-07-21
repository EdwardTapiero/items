package co.com.mercadolibre.items.repository;

import co.com.mercadolibre.items.model.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ItemRepository extends ReactiveMongoRepository<Item, String> {

    @Query("{ id: { $exists: true }}")
    Flux<Item> retrieveAllItemsPaged(final Pageable page);

}
