package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.OrderEntity;
import app.application.adapters.persistence.sql.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByPatient(PatientEntity patient);
}
