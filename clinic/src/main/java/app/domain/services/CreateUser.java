package app.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.User;
import app.domain.ports.UserPort;

@Service
public class CreateUser {

    @Autowired
    private UserPort userPort;

    public CreateUser(UserPort userPort) {
        this.userPort = userPort;
    }

    public void createUser(User user) throws BusinessException {
        //vamos a validar que no haya otro usuario con la misma cedula
        //para eso debemos consultar a la base de datos si existe un usuario con esa cedula
        //si existe, lanzamos una excepcion
        if (userPort.existsByDocument(user.getDocument())) {
            throw new BusinessException("Ya existe un usuario con esa cedula");  
        }
        //validamos la existencia del usuario por username
        //en caso de existir lanzamos una excepcion
        if(userPort.existsByUsername(user.getUsername())){
            throw new BusinessException("Ya existe un usuario con ese username");
        }
        //si no existe, podemos crear el usuario
        userPort.save(user);

    }
    
}
