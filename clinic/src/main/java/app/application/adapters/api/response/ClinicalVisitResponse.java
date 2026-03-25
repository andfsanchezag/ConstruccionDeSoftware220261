package app.application.adapters.api.response;

import java.sql.Date;

public record ClinicalVisitResponse(
        long id,
        String patientDocument,
        String patientName,
        String nurseDocument,
        String nurseName,
        Date date,
        String bloodPressure,
        double temperature,
        int pulse,
        double oxygenLevel,
        String observations,
        long orderId
) {}
