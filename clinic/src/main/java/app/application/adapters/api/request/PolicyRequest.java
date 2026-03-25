package app.application.adapters.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Date expiryDate;

    private long companyId;

    private String companyName;
}
