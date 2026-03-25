package app.application.adapters.api.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PolicyRequest {

    private long id;

    @NotBlank(message = "El número de póliza es obligatorio")
    private String policyNumber;

    private boolean active;

    @NotNull(message = "La fecha de expiración es obligatoria")
    @Future(message = "La fecha de expiración debe ser en el futuro")
    private Date expiryDate;

    @Positive(message = "El ID de la compañía debe ser mayor a cero")
    private long companyId;

    @NotBlank(message = "El nombre de la compañía es obligatorio")
    private String companyName;
}
