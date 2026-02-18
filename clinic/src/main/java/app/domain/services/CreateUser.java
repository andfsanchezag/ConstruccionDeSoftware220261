package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.User;
import app.domain.ports.UserPort;

public class CreateUser {

    private UserPort userPort;

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
