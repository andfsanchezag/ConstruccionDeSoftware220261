package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    boolean existsByDocument(String document);
    PatientEntity findByDocument(String document);
    void deleteByDocument(String document);
}
