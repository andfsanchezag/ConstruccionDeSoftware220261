package app.domain.models.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a medicine that can be prescribed in a medical order.
 * Inherits: id, name, price from InventoryItem.
 */
@Setter
@Getter
@NoArgsConstructor
public class Medicine extends InventoryItem {
}
