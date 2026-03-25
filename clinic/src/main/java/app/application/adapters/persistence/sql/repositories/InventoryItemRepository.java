package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.InventoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItemEntity, Long> {
}
