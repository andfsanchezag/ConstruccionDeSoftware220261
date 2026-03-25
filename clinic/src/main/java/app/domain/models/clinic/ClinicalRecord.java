package app.domain.models.clinic;

import app.domain.models.identity.User;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class ClinicalRecord {

    private Patient patient;

    private Date date;

    private User doctor;

    private String reason;

    private String symptoms;

    private String diagnosis;

    private Order order;
}
