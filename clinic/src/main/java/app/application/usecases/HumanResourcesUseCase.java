package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class HumanResourcesUseCase implements app.domain.ports.in.HumanResourcesUseCase {

    @Autowired
    private CreateUser createUser;
    @Autowired
    private UpdateUser updateUser;
    @Autowired
    private DeleteUser deleteUser;
    @Autowired
    private FindUser findUser;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public HumanResourcesUseCase(CreateUser createUser, UpdateUser updateUser,
                                  DeleteUser deleteUser, FindUser findUser,
                                  PasswordEncoder passwordEncoder) {
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
        this.findUser = findUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createHumanResources(User user) throws BusinessException {
        user.setRole(Role.HUMANRESOURCES);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.createUser(user);
    }

    @Override
    public void createDoctor(User user) throws BusinessException {
        user.setRole(Role.DOCTOR);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.createUser(user);
    }

    @Override
    public void createNurse(User user) throws BusinessException {
        user.setRole(Role.NURSE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.createUser(user);
    }

    @Override
    public void createAdministrative(User user) throws BusinessException {
        user.setRole(Role.ADMINISTRATIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.createUser(user);
    }

    @Override
    public void updateUser(User user) throws BusinessException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser.updateUser(user);
    }

    @Override
    public void deleteUser(String document) throws BusinessException {
        deleteUser.deleteUser(document);
    }

    @Override
    public User findUserByDocument(String document) throws BusinessException {
        return findUser.findByDocument(document);
    }

    @Override
    public List<User> findAllUsers() {
        return findUser.findAll();
    }

}
