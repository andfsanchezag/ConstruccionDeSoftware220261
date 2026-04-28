package app.domain.services;

import app.domain.Exceptions.NotFoundException;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.ports.out.OrderPort;
import app.domain.ports.out.PatientPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FindOrder {

    private OrderPort orderPort;
    private PatientPort patientPort;

    @Autowired
    public FindOrder(OrderPort orderPort, PatientPort patientPort) {
        this.orderPort = orderPort;
        this.patientPort = patientPort;
    }

    public Order findById(long id) throws NotFoundException {
        Order order = orderPort.findById(id);
        if (order == null) {
            throw new NotFoundException("No existe una orden con ese id");
        }
        return order;
    }

    public List<Order> findByPatient(String patientDocument) throws NotFoundException {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new NotFoundException("No existe un paciente con esa cedula");
        }
        return orderPort.findByPatient(patient);
    }
}
