package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("MEDICALSUPPORT")
public class DiagnosticSupportEntity extends InventoryItemEntity {
}
