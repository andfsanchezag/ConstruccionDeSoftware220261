package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.ports.out.UserPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUser {

    private UserPort userPort;

    @Autowired
    public DeleteUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public void deleteUser(String document) throws BusinessException {
        if (!userPort.existsByDocument(document)) {
            throw new BusinessException("No existe un usuario con esa cedula");
        }
        userPort.deleteByDocument(document);
    }
}
