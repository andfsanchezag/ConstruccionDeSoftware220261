package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.enums.Role;
import app.domain.models.identity.User;
import app.domain.services.CreateUser;
import app.domain.services.DeleteUser;
import app.domain.services.FindUser;
import app.domain.services.UpdateUser;

import java.util.List;

@Service
public class HumanResourcesUseCase {

    @Autowired
    private CreateUser createUser;
    @Autowired
    private UpdateUser updateUser;
    @Autowired
    private DeleteUser deleteUser;
    @Autowired
    private FindUser findUser;

    public HumanResourcesUseCase(CreateUser createUser, UpdateUser updateUser,
                                  DeleteUser deleteUser, FindUser findUser) {
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
        this.findUser = findUser;
    }

    public void createHumanResources(User user) throws BusinessException {
        user.setRole(Role.HUMANRESOURCES);
        createUser.createUser(user);
    }

    public void createDoctor(User user) throws BusinessException {
        user.setRole(Role.DOCTOR);
        createUser.createUser(user);
    }

    public void createNurse(User user) throws BusinessException {
        user.setRole(Role.NURSE);
        createUser.createUser(user);
    }

    public void createAdministrative(User user) throws BusinessException {
        user.setRole(Role.ADMINISTRATIVE);
        createUser.createUser(user);
    }

    public void updateUser(User user) throws BusinessException {
        updateUser.updateUser(user);
    }

    public void deleteUser(String document) throws BusinessException {
        deleteUser.deleteUser(document);
    }

    public User findUserByDocument(String document) throws BusinessException {
        return findUser.findByDocument(document);
    }

    public List<User> findAllUsers() {
        return findUser.findAll();
    }

}
