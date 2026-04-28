package app.domain.ports.in;

import app.domain.Exceptions.BusinessException;

public interface AuthUseCase {

    String login(String username, String password) throws BusinessException;

}
