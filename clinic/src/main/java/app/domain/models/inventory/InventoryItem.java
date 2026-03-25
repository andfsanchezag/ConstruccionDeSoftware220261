package app.domain.models.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base abstract class for all inventory items managed by the clinic.
 * Provides common identity and pricing attributes.
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class InventoryItem {

    private long id;

    private String name;

    private double price;
}
