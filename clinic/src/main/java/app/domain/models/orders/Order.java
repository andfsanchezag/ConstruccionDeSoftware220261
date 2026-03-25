package app.domain.models.orders;

import app.domain.models.identity.User;
import app.domain.models.patient.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Order {

    private long id;

    private Patient patient;

    private User doctor;

    private Date date;

    private List<OrderItem> orderItems;
}
