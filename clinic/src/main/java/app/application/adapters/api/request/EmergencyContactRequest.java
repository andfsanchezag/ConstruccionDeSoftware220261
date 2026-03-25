package app.application.adapters.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class EmergencyContactRequest {

    @NotBlank(message = "El documento del contacto de emergencia es obligatorio")
    private String document;

    @NotBlank(message = "El nombre del contacto de emergencia es obligatorio")
    private String name;

    @NotBlank(message = "El teléfono del contacto de emergencia es obligatorio")
    private String phone;

    @Email(message = "El correo debe tener un formato válido")
    private String email;

    private String address;

    private Date birthDate;

    @NotBlank(message = "La relación con el paciente es obligatoria")
    private String relationship;
}
