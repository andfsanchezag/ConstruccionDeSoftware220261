package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.identity.User;
import app.domain.ports.out.UserPort;
import app.infrastructure.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginUser {

    private final UserPort userPort;
    private final JwtUtil jwtUtil;

    public LoginUser(UserPort userPort, JwtUtil jwtUtil) {
        this.userPort = userPort;
        this.jwtUtil = jwtUtil;
    }

    public String execute(String username, String password) throws BusinessException {
        User user = userPort.findByUsername(username);
        if (user == null) {
            throw new BusinessException("Credenciales inválidas");
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("Credenciales inválidas");
        }
        return jwtUtil.generateToken(user.getDocument(), user.getUsername(), user.getRole().name());
    }
}
