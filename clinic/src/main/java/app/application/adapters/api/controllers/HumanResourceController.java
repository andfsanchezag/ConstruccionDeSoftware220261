package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.UserRequest;
import app.application.adapters.api.response.UserResponse;
import app.application.usecases.HumanResourcesUseCase;
import app.domain.models.identity.User;

import java.util.List;

@RestController
@RequestMapping("/human-resources")
public class HumanResourceController {

    @Autowired
    private HumanResourcesUseCase humanResourcesUseCase;

    public HumanResourceController(HumanResourcesUseCase humanResourcesUseCase) {
        this.humanResourcesUseCase = humanResourcesUseCase;
    }

    @PostMapping("/doctors")
    public ResponseEntity<UserResponse> createDoctor(@Valid @RequestBody UserRequest userRequest) {
        User user = toUser(userRequest);
        humanResourcesUseCase.createDoctor(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(user));
    }

    @PostMapping("/nurses")
    public ResponseEntity<UserResponse> createNurse(@Valid @RequestBody UserRequest userRequest) {
        User user = toUser(userRequest);
        humanResourcesUseCase.createNurse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(user));
    }

    @PostMapping("/administrative")
    public ResponseEntity<UserResponse> createAdministrative(@Valid @RequestBody UserRequest userRequest) {
        User user = toUser(userRequest);
        humanResourcesUseCase.createAdministrative(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(user));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createHumanResources(@Valid @RequestBody UserRequest userRequest) {
        User user = toUser(userRequest);
        humanResourcesUseCase.createHumanResources(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(user));
    }

    @PutMapping("/{document}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String document,
                                                    @Valid @RequestBody UserRequest userRequest) {
        userRequest.setDocument(document);
        User user = toUser(userRequest);
        humanResourcesUseCase.updateUser(user);
        return ResponseEntity.ok(toResponse(user));
    }

    @DeleteMapping("/{document}")
    public ResponseEntity<Void> deleteUser(@PathVariable String document) {
        humanResourcesUseCase.deleteUser(document);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{document}")
    public ResponseEntity<UserResponse> findUser(@PathVariable String document) {
        User user = humanResourcesUseCase.findUserByDocument(document);
        return ResponseEntity.ok(toResponse(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> users = humanResourcesUseCase.findAllUsers()
                .stream()
                .map(HumanResourceController::toResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    private static User toUser(UserRequest req) {
        User user = new User();
        user.setId(req.getId());
        user.setDocument(req.getDocument());
        user.setName(req.getName());
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setRole(req.getRole());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setAddress(req.getAddress());
        user.setBirthDate(req.getBirthDate());
        return user;
    }

    private static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getDocument(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getAddress(),
                user.getBirthDate(),
                user.getUsername(),
                user.getRole()
        );
    }
}
