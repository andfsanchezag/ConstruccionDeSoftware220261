package app.application.adapters.api.request;

import app.domain.models.enums.ItemType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    private long inventoryItemId;

    @NotNull(message = "El tipo de item es obligatorio")
    private ItemType itemType;
}
