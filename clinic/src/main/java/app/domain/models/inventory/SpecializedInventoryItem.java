package app.domain.models.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract base for inventory items that may require a specialist
 * (procedures and diagnostic support). Extends InventoryItem.
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class SpecializedInventoryItem extends InventoryItem {

    private boolean requiresSpecialist;

    private Specialty specialty;
}
