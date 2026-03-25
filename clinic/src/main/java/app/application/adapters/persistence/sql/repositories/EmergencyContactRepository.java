package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.EmergencyContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContactEntity, Long> {
    boolean existsByDocument(String document);
    EmergencyContactEntity findByDocument(String document);
}
