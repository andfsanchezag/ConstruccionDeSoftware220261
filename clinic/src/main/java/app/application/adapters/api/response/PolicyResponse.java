package app.application.adapters.api.response;

import java.sql.Date;

public record PolicyResponse(
        long id,
        String policyNumber,
        boolean active,
        Date expiryDate,
        long companyId,
        String companyName
) {}
