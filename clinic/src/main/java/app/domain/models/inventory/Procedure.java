package app.domain.models.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a medical procedure that can be ordered for a patient.
 * Inherits: id, name, price, requiresSpecialist, specialty from SpecializedInventoryItem.
 */
@Setter
@Getter
@NoArgsConstructor
public class Procedure extends SpecializedInventoryItem {
}
