package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.clinic.ClinicalVisit;
import app.domain.models.enums.Role;
import app.domain.models.identity.User;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.ports.out.ClinicalVisitPort;
import app.domain.ports.out.OrderPort;
import app.domain.ports.out.PatientPort;
import app.domain.ports.out.UserPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CreateClinicalVisit {

    private ClinicalVisitPort clinicalVisitPort;
    private PatientPort patientPort;
    private UserPort userPort;
    private OrderPort orderPort;

    @Autowired
    public CreateClinicalVisit(ClinicalVisitPort clinicalVisitPort, PatientPort patientPort,
                               UserPort userPort, OrderPort orderPort) {
        this.clinicalVisitPort = clinicalVisitPort;
        this.patientPort = patientPort;
        this.userPort = userPort;
        this.orderPort = orderPort;
    }

    public void createClinicalVisit(ClinicalVisit visit) throws BusinessException {
        Patient patient = patientPort.findByDocument(visit.getPatient().getDocument());
        if (patient == null) {
            throw new BusinessException("No existe el paciente");
        }
        User nurse = userPort.findByDocument(visit.getNurse().getDocument());
        if (nurse == null) {
            throw new BusinessException("No existe el enfermero");
        }
        if (!nurse.getRole().equals(Role.NURSE)) {
            throw new BusinessException("El usuario no es una enfermera");
        }
        if (visit.getOrder() == null) {
            throw new BusinessException("La visita clinica debe tener una orden asociada");
        }
        Order order = orderPort.findById(visit.getOrder().getId());
        if (order == null) {
            throw new BusinessException("No existe la orden asociada");
        }
        visit.setDate(new Date(System.currentTimeMillis()));
        visit.setPatient(patient);
        visit.setNurse(nurse);
        visit.setOrder(order);
        clinicalVisitPort.save(visit);
    }
}
