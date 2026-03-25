package app.application.usecases;

import app.domain.Exceptions.BusinessException;
import app.domain.models.identity.User;
import app.domain.ports.UserPort;
import app.infrastructure.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCase {

    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthUseCase(UserPort userPort, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userPort = userPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) throws BusinessException {
        User user = userPort.findByUsername(username);
        if (user == null) {
            throw new BusinessException("Credenciales inválidas");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("Credenciales inválidas");
        }
        return jwtUtil.generateToken(user.getDocument(), user.getUsername(), user.getRole().name());
    }
}
