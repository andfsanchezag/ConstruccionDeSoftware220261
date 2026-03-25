package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.identity.User;
import app.domain.ports.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUser {

    private UserPort userPort;

    @Autowired
    public CreateUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public void createUser(User user) throws BusinessException {
        if (userPort.existsByDocument(user.getDocument())) {
            throw new BusinessException("Ya existe un usuario con esa cedula");
        }
        if (userPort.existsByUsername(user.getUsername())) {
            throw new BusinessException("Ya existe un usuario con ese username");
        }
        userPort.save(user);
    }
}
