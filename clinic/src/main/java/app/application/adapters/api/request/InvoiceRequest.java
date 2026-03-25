package app.application.adapters.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InvoiceRequest {

    @NotBlank(message = "El documento del paciente es obligatorio")
    private String patientDocument;

    @NotEmpty(message = "La factura debe tener al menos un item")
    @Valid
    private List<InvoiceItemRequest> items;
}
