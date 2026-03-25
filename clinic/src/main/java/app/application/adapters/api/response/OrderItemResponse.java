package app.application.adapters.api.response;

import app.domain.models.enums.ItemType;

public record OrderItemResponse(
        long id,
        long inventoryItemId,
        String inventoryItemName,
        double inventoryItemPrice,
        ItemType itemType
) {}
