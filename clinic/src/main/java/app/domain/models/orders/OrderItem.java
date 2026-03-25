package app.domain.models.orders;

import app.domain.models.enums.ItemType;
import app.domain.models.inventory.InventoryItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderItem {

    private long id;

    private InventoryItem inventoryItem;

    private ItemType itemType;
}
