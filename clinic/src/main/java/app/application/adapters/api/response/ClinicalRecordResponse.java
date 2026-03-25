package app.application.adapters.api.response;

import java.sql.Date;

public record ClinicalRecordResponse(
        String patientDocument,
        String patientName,
        String doctorDocument,
        String doctorName,
        Date date,
        String reason,
        String symptoms,
        String diagnosis,
        long orderId
) {}
