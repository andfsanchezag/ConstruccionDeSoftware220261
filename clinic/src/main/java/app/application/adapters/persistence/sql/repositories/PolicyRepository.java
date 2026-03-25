package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> {
}
