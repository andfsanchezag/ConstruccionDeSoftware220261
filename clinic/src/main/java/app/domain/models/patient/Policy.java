package app.domain.models.patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class Policy {

    private long id;

    private Company company;

    private String policyNumber;

    private boolean active;

    private Date expiryDate;
}
