package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.ClinicalVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalVisitRepository extends JpaRepository<ClinicalVisitEntity, Long> {
}
