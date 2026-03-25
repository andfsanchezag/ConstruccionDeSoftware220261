package app.application.adapters.api.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicalVisitRequest {

    @NotBlank(message = "El documento del paciente es obligatorio")
    private String patientDocument;

    @NotBlank(message = "La presión arterial es obligatoria")
    private String bloodPressure;

    @Positive(message = "La temperatura debe ser mayor a cero")
    private double temperature;

    @Positive(message = "El pulso debe ser mayor a cero")
    private int pulse;

    @DecimalMin(value = "0.0", message = "El nivel de oxígeno no puede ser negativo")
    @DecimalMax(value = "100.0", message = "El nivel de oxígeno no puede superar 100")
    private double oxygenLevel;

    private String observations;

    private long orderId;
}
