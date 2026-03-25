package app.application.adapters.persistence.sql;

import app.domain.ports.UserPort;
import app.domain.models.identity.User;
import app.domain.models.enums.Role;
import app.application.adapters.persistence.sql.repositories.UserRepository;
import app.application.adapters.persistence.sql.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserPersistenceAdapter implements UserPort {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(toEntity(user));
    }

    @Override
    public void update(User user) {
        UserEntity existing = userRepository.findByDocument(user.getDocument());
        if (existing != null) {
            existing.setName(user.getName());
            existing.setPhone(user.getPhone());
            existing.setEmail(user.getEmail());
            existing.setAddress(user.getAddress());
            existing.setBirthDate(user.getBirthDate());
            existing.setUsername(user.getUsername());
            existing.setPassword(user.getPassword());
            existing.setRole(user.getRole().toString());
            userRepository.save(existing);
        }
    }

    @Override
    public void deleteByDocument(String document) {
        userRepository.deleteByDocument(document);
    }

    @Override
    public boolean existsByDocument(String document) {
        return userRepository.existsByDocument(document);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsernameAndDocumentNot(String username, String document) {
        return userRepository.existsByUsernameAndDocumentNot(username, document);
    }

    @Override
    public boolean existsByEmailAndDocumentNot(String email, String document) {
        return userRepository.existsByEmailAndDocumentNot(email, document);
    }

    @Override
    public User findByDocument(String document) {
        return toModel(userRepository.findByDocument(document));
    }

    @Override
    public User findByUsername(String username) {
        return toModel(userRepository.findByUsername(username));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    private UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setDocument(user.getDocument());
        userEntity.setPhone(user.getPhone());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setBirthDate(user.getBirthDate());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole().toString());
        return userEntity;
    }

    private User toModel(UserEntity e) {
        if (e == null) return null;
        User user = new User();
        user.setId(e.getId());
        user.setName(e.getName());
        user.setDocument(e.getDocument());
        user.setPhone(e.getPhone());
        user.setEmail(e.getEmail());
        user.setAddress(e.getAddress());
        user.setBirthDate(e.getBirthDate());
        user.setUsername(e.getUsername());
        user.setPassword(e.getPassword());
        user.setRole(Role.valueOf(e.getRole()));
        return user;
    }
}
