package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.identity.User;
import app.domain.ports.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUser {

    private UserPort userPort;

    @Autowired
    public UpdateUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public void updateUser(User user) throws BusinessException {
        if (!userPort.existsByDocument(user.getDocument())) {
            throw new BusinessException("No existe un usuario con esa cedula");
        }
        userPort.update(user);
    }
}
