package app.application.adapters.api.request;

import app.domain.models.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PatientRequest {

    @NotBlank(message = "El documento del paciente es obligatorio")
    private String document;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    private String name;

    @NotBlank(message = "El teléfono es obligatorio")
    private String phone;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    private Date birthDate;

    @NotNull(message = "El género es obligatorio")
    private Gender gender;

    @Valid
    @NotNull(message = "El contacto de emergencia es obligatorio")
    private EmergencyContactRequest emergencyContact;
}
