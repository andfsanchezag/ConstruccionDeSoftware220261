package app.application.adapters.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicalRecordRequest {

    @NotBlank(message = "El documento del paciente es obligatorio")
    private String patientDocument;

    @NotBlank(message = "El motivo de la consulta es obligatorio")
    private String reason;

    @NotBlank(message = "Los síntomas son obligatorios")
    private String symptoms;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnosis;

    private long orderId;
}
