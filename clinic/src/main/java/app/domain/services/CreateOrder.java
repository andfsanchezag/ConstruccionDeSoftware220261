package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.enums.ItemType;
import app.domain.models.enums.Role;
import app.domain.models.orders.Order;
import app.domain.models.orders.OrderItem;
import app.domain.models.identity.User;
import app.domain.models.patient.Patient;
import app.domain.ports.out.OrderPort;
import app.domain.ports.out.PatientPort;
import app.domain.ports.out.UserPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CreateOrder {

    private PatientPort patientPort;
    private UserPort userPort;
    private OrderPort orderPort;

    @Autowired
    public CreateOrder(PatientPort patientPort, UserPort userPort, OrderPort orderPort) {
        this.patientPort = patientPort;
        this.userPort = userPort;
        this.orderPort = orderPort;
    }

    public void createOrder(Order order) throws BusinessException {
        Patient patient = patientPort.findByDocument(order.getPatient().getDocument());
        if (patient == null) {
            throw new BusinessException("No existe el paciente");
        }
        User doctor = userPort.findByDocument(order.getDoctor().getDocument());
        if (doctor == null) {
            throw new BusinessException("No existe el doctor");
        }
        if (!doctor.getRole().equals(Role.DOCTOR)) {
            throw new BusinessException("El usuario no es un doctor");
        }
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new BusinessException("La orden debe tener al menos un item");
        }
        if (hasMixedTypes(order)) {
            throw new BusinessException("La orden de tipo ayuda diagnostica no puede tener items de tipo medicamento o procedimiento");
        }
        order.setDate(new Date(System.currentTimeMillis()));
        order.setPatient(patient);
        order.setDoctor(doctor);
        orderPort.save(order);
    }

    private boolean hasMixedTypes(Order order) {
        boolean hasMedicineOrProcedure = false;
        boolean hasSupport = false;
        for (OrderItem item : order.getOrderItems()) {
            if (item.getItemType().equals(ItemType.MEDICINE) || item.getItemType().equals(ItemType.PROCEDURE)) {
                hasMedicineOrProcedure = true;
            }
            if (item.getItemType().equals(ItemType.MEDICALSUPPORT)) {
                hasSupport = true;
            }
            if (hasMedicineOrProcedure && hasSupport) {
                return true;
            }
        }
        return false;
    }
}
