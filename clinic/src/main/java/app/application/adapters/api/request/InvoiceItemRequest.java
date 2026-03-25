package app.application.adapters.api.request;

import app.domain.models.enums.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceItemRequest {

    private long orderId;

    @NotNull(message = "El tipo de item es obligatorio")
    private ItemType itemType;

    @NotBlank(message = "El nombre del item es obligatorio")
    private String itemName;

    @Positive(message = "La cantidad debe ser mayor a cero")
    private int quantity;

    @Positive(message = "El precio unitario debe ser mayor a cero")
    private double unitPrice;
}
