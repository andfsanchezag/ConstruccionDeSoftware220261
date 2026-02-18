package app.domain.ports;

public interface UserPort {

    public boolean existsByDocument(String cedula);
    public boolean existsByUsername(String username);
    public void save(app.domain.models.User user);
    
}
