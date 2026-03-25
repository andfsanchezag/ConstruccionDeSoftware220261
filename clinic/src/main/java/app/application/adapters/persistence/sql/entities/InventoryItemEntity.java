package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_category", discriminatorType = DiscriminatorType.STRING)
@Table(name = "inventory_items")
public abstract class InventoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "requires_specialist")
    private Boolean requiresSpecialist;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private SpecialtyEntity specialty;
}
