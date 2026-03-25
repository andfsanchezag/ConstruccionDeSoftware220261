package app.application.adapters.api.response;

import app.domain.models.enums.Gender;

import java.sql.Date;

public record PatientResponse(
        long id,
        String document,
        String name,
        String phone,
        String email,
        String address,
        Date birthDate,
        Gender gender,
        EmergencyContactResponse emergencyContact,
        PolicyResponse policy
) {}
