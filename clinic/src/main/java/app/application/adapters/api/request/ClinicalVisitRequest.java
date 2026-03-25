package app.application.adapters.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicalVisitRequest {

    @NotBlank(message = "El documento del paciente es obligatorio")
    private String patientDocument;

    @NotBlank(message = "El documento de la enfermera es obligatorio")
    private String nurseDocument;

    @NotBlank(message = "La presión arterial es obligatoria")
    private String bloodPressure;

    private double temperature;

    @Positive(message = "El pulso debe ser mayor a cero")
    private int pulse;

    private double oxygenLevel;

    private String observations;

    private long orderId;
}
