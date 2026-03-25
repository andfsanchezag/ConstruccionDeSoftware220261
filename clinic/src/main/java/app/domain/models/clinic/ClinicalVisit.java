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
public class ClinicalVisit {

    private long id;

    private Patient patient;

    private User nurse;

    private Date date;

    private String bloodPressure;

    private double temperature;

    private int pulse;

    private double oxygenLevel;

    private String observations;

    private Order order;
}
