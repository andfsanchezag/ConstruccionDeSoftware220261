package app.domain.models.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a diagnostic support item (e.g. lab tests, imaging).
 * Inherits: id, name, price, requiresSpecialist, specialty from SpecializedInventoryItem.
 */
@Setter
@Getter
@NoArgsConstructor
public class DiagnosticSupport extends SpecializedInventoryItem {
}
