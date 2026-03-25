package app.application.adapters.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotBlank(message = "El documento del paciente es obligatorio")
    private String patientDocument;

    // Requerido cuando el administrativo crea la orden en nombre de un médico.
    // Cuando un médico la crea directamente, este campo es ignorado y se toma del token JWT.
    private String doctorDocument;

    @NotEmpty(message = "La orden debe tener al menos un item")
    @Valid
    private List<OrderItemRequest> orderItems;
}
