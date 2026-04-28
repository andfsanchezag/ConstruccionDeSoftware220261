package app.domain.services;

import app.domain.Exceptions.NotFoundException;
import app.domain.models.identity.User;
import app.domain.ports.out.UserPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FindUser {

    private UserPort userPort;

    @Autowired
    public FindUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public User findByDocument(String document) throws NotFoundException {
        User user = userPort.findByDocument(document);
        if (user == null) {
            throw new NotFoundException("No existe un usuario con esa cedula");
        }
        return user;
    }

    public List<User> findAll() {
        return userPort.findAll();
    }
}
