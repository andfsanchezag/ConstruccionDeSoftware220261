package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.application.adapters.api.request.UserRequest;
import app.application.usecases.HumanResourcesUseCase;
import app.domain.models.User;

@RestController("/human_resources")
public class HumanResourceController {
    
    @Autowired
    private HumanResourcesUseCase humanResourcesUseCase;
    
    public HumanResourceController(HumanResourcesUseCase humanResourcesUseCase) {
        this.humanResourcesUseCase = humanResourcesUseCase;
    }

    @PostMapping("/doctor")
    public void createDoctor(@Valid @RequestBody UserRequest userRequest) {
        humanResourcesUseCase.createDoctor(toUser(userRequest));
    }

    @PostMapping("/nurse")
    public void createNurse(@Valid @RequestBody UserRequest userRequest) {
        humanResourcesUseCase.createNurse(toUser(userRequest));
    }

    @PostMapping("/administrative")
    public void createAdministrative(@Valid @RequestBody UserRequest userRequest) { 
        humanResourcesUseCase.createAdministrative(toUser(userRequest));
    }

    @PostMapping("/human_resources")
    public void createHumanResources(@Valid @RequestBody UserRequest userRequest) {
        humanResourcesUseCase.createHumanResources(toUser(userRequest));
    }

    private User toUser(UserRequest userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setDocument(userRequest.getDocument());
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setBirthDate(userRequest.getBirthDate());
        return user;
    }

    /*
        Post para crear recursos
        Put para actualizar recursos
        Delete para eliminar recursos
        Get para consultar recursos
    */

        /*
        www.misitio.com/doctor -> POST -> crear doctor
        www.misitio.com/doctor/{id} -> PUT -> actualizar doctor
        www.misitio.com/doctor/{id} -> DELETE -> eliminar doctor
        www.misitio.com/doctor/{id} -> GET -> consultar doctor
        */

}
