package app.application.adapters.api.response;

import java.sql.Date;
import java.util.List;

public record InvoiceResponse(
        long id,
        String patientDocument,
        String patientName,
        Date issueDate,
        double totalAmount,
        double copayment,
        double insuranceCoverage,
        double patientPayment,
        boolean policyApplied,
        List<InvoiceItemResponse> items
) {}
