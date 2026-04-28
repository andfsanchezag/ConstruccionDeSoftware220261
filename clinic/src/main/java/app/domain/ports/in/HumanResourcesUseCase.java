package app.domain.ports.in;

import app.domain.Exceptions.BusinessException;
import app.domain.models.identity.User;

import java.util.List;

public interface HumanResourcesUseCase {

    void createHumanResources(User user) throws BusinessException;

    void createDoctor(User user) throws BusinessException;

    void createNurse(User user) throws BusinessException;

    void createAdministrative(User user) throws BusinessException;

    void updateUser(User user) throws BusinessException;

    void deleteUser(String document) throws BusinessException;

    User findUserByDocument(String document) throws BusinessException;

    List<User> findAllUsers();

}
