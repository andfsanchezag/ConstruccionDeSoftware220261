package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
