package app.application.adapters.api.response;

import java.sql.Date;
import java.util.List;

public record OrderResponse(
        long id,
        String patientDocument,
        String patientName,
        String doctorDocument,
        String doctorName,
        Date date,
        List<OrderItemResponse> orderItems
) {}
