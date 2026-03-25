package app.domain.ports;

import app.domain.models.identity.User;
import java.util.List;

public interface UserPort {

    boolean existsByDocument(String document);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndDocumentNot(String username, String document);
    boolean existsByEmailAndDocumentNot(String email, String document);
    void save(User user);
    void update(User user);
    void deleteByDocument(String document);
    User findByDocument(String document);
    User findByUsername(String username);
    List<User> findAll();

}
