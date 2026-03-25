package app.application.adapters.api.response;

import java.sql.Date;

public record EmergencyContactResponse(
        long id,
        String document,
        String name,
        String phone,
        String email,
        String address,
        Date birthDate,
        String relationship
) {}
