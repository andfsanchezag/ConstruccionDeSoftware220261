package app.application.adapters.api.response;

import app.domain.models.enums.ItemType;

public record InvoiceItemResponse(
        long id,
        long orderId,
        ItemType itemType,
        String itemName,
        int quantity,
        double unitPrice,
        double totalPrice
) {}
