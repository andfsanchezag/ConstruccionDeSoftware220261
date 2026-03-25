package app.domain.models.identity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmergencyContact extends Person {

    private String relationship;
}
