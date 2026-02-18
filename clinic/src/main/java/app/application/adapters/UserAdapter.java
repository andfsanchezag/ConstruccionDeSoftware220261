package app.application.adapters;

import app.domain.ports.UserPort;

public class UserAdapter implements UserPort {

    @Override
    public boolean existsByDocument(String cedula) {
        //aqui iria la logica para consultar a la base de datos si existe un usuario con esa cedula
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        //aqui iria la logica para consultar a la base de datos si existe un usuario con ese username
        return false;
    }

    @Override
    public void save(app.domain.models.User user) {
        //aqui iria la logica para guardar el usuario en la base de datos
    }
    
}
