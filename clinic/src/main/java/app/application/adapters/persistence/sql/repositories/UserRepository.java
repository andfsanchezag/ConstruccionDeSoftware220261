package app.application.adapters.persistence.sql.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import app.application.adapters.persistence.sql.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public boolean existsByDocument(String document);
    public boolean existsByUsername(String username);
    public UserEntity findByDocument(String document);
    
}
