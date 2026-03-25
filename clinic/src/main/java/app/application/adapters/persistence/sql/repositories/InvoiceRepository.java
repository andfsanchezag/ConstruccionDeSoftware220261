package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.InvoiceEntity;
import app.application.adapters.persistence.sql.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    List<InvoiceEntity> findByPatient(PatientEntity patient);
}
