package co.com.mercadolibre.items.model.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;

@Data
@RedisHash("items")
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1797766805873247662L;

    private String id;
    @NotNull(message = "The 'name' field is required")
    private String name;
    private String description;
    @NotNull(message = "The 'price' field is required")
    private Double price;

}
