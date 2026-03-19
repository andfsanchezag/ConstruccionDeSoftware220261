package app.application.adapters.persistence.sql;

import app.domain.ports.UserPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.domain.models.Role;
import app.domain.models.User;
import app.application.adapters.persistence.sql.repositories.UserRepository;
import app.application.adapters.persistence.sql.entities.UserEntity;


@Service
public class UserPersistenceAdapter implements UserPort {

    @Autowired
    private UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = toEntity(user);
        userRepository.save(userEntity);
    }
    @Override
    public boolean existsByDocument(String document){
        return userRepository.existsByDocument(document);
    }
    @Override
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByDocument(User user) {
        UserEntity userEntity = userRepository.findByDocument(user.getDocument());
        return toModel(userEntity);
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

    private User toModel(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        User user = new User();
        user.setName(userEntity.getName());
        user.setDocument(userEntity.getDocument());
        user.setPhone(userEntity.getPhone());
        user.setEmail(userEntity.getEmail());
        user.setAddress(userEntity.getAddress());
        user.setBirthDate(userEntity.getBirthDate());
        user.setUsername(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setRole(Role.valueOf(userEntity.getRole()));
        return user;
    }

    
}
