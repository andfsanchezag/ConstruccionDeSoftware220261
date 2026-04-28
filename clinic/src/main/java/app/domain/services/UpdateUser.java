package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.identity.User;
import app.domain.ports.out.UserPort;

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
        if (userPort.existsByUsernameAndDocumentNot(user.getUsername(), user.getDocument())) {
            throw new BusinessException("Ya existe otro usuario con ese username");
        }
        if (userPort.existsByEmailAndDocumentNot(user.getEmail(), user.getDocument())) {
            throw new BusinessException("Ya existe otro usuario con ese email");
        }
        userPort.update(user);
    }
}
