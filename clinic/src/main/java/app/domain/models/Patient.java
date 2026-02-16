package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Setter
public class Patient extends Person {
    private boolean gender;
    private EmergencyContact emergencyContact;
    private Policy policy;
}
