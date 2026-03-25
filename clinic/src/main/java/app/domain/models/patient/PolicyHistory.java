package app.domain.models.patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PolicyHistory {

    private long id;

    private Patient patient;

    private int year;

    private double accumulatedCopayment;
}
