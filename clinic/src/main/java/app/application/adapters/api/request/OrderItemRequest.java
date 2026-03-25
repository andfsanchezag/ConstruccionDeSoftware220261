package app.application.adapters.api.request;

import app.domain.models.enums.ItemType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    @Positive(message = "El ID del ítem de inventario debe ser mayor a cero")
    private long inventoryItemId;

    @NotNull(message = "El tipo de item es obligatorio")
    private ItemType itemType;
}
