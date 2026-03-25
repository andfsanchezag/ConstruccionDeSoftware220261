package app.domain.ports;

import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import java.util.List;

public interface OrderPort {

    void save(Order order);
    Order findById(long id);
    List<Order> findByPatient(Patient patient);

}
