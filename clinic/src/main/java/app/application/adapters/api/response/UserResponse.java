package app.application.adapters.api.response;

import app.domain.models.enums.Role;

import java.sql.Date;

public record UserResponse(
        long id,
        String document,
        String name,
        String phone,
        String email,
        String address,
        Date birthDate,
        String username,
        Role role
) {}
