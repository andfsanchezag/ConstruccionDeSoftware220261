package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.PatientEntity;
import app.application.adapters.persistence.sql.entities.PolicyHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyHistoryRepository extends JpaRepository<PolicyHistoryEntity, Long> {
    Optional<PolicyHistoryEntity> findByPatientAndYear(PatientEntity patient, int year);
}
