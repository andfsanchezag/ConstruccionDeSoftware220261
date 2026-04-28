package app.application.usecases;

import app.domain.Exceptions.BusinessException;
import app.domain.services.LoginUser;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCase implements app.domain.ports.in.AuthUseCase {

    private final LoginUser loginUser;

    public AuthUseCase(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    @Override
    public String login(String username, String password) throws BusinessException {
        return loginUser.execute(username, password);
    }
}
