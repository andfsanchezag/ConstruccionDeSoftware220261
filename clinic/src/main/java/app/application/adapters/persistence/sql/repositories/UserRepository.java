package app.application.adapters.persistence.sql.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import app.application.adapters.persistence.sql.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByDocument(String document);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndDocumentNot(String username, String document);
    boolean existsByEmailAndDocumentNot(String email, String document);
    UserEntity findByDocument(String document);
    void deleteByDocument(String document);
}
