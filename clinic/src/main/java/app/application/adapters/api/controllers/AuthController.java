package app.application.adapters.api.controllers;

import app.application.adapters.api.request.LoginRequest;
import app.application.adapters.api.response.LoginResponse;
import app.application.usecases.AuthUseCase;
import app.infrastructure.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;
    private final JwtUtil jwtUtil;

    public AuthController(AuthUseCase authUseCase, JwtUtil jwtUtil) {
        this.authUseCase = authUseCase;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authUseCase.login(request.getUsername(), request.getPassword());
        String document = jwtUtil.extractDocument(token);
        String role = jwtUtil.extractRole(token);
        return ResponseEntity.ok(new LoginResponse(token, document, role));
    }
}
