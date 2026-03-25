package app.domain.ports;

import app.domain.models.identity.User;
import java.util.List;

public interface UserPort {

    boolean existsByDocument(String document);
    boolean existsByUsername(String username);
    void save(User user);
    void update(User user);
    void deleteByDocument(String document);
    User findByDocument(String document);
    List<User> findAll();

}
