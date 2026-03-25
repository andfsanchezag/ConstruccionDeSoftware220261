package app.domain.models.patient;

import app.domain.models.enums.Gender;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.identity.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Patient extends Person {

    private Gender gender;

    private EmergencyContact emergencyContact;

    private Policy policy;
}
