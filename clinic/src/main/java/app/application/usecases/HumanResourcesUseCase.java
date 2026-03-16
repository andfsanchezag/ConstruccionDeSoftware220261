package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.Role;
import app.domain.models.User;
import app.domain.services.CreateUser;

@Service
public class HumanResourcesUseCase {
    
    @Autowired
    private CreateUser createUser;

    public HumanResourcesUseCase(CreateUser createUser) {
        this.createUser = createUser;
    }

    public void createHumanResources(User user) {
        user.setRole(Role.HUMANRESOURCES);
        createUser.createUser(user);
    }

    public void createDoctor(User user) {
        user.setRole(Role.DOCTOR);
        createUser.createUser(user);
    }

    public void createNurse(User user) {
        user.setRole(Role.NURSE);
        createUser.createUser(user);
    }

    public void createAdministrative(User user) {
        user.setRole(Role.ADMINISTRATIVE);
        createUser.createUser(user);
    }
    
}
