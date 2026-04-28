package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.enums.Role;
import app.domain.models.identity.User;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.ports.out.ClinicalRecordPort;
import app.domain.ports.out.OrderPort;
import app.domain.ports.out.PatientPort;
import app.domain.ports.out.UserPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CreateClinicalRecord {

    private ClinicalRecordPort clinicalRecordPort;
    private PatientPort patientPort;
    private UserPort userPort;
    private OrderPort orderPort;

    @Autowired
    public CreateClinicalRecord(ClinicalRecordPort clinicalRecordPort, PatientPort patientPort,
                                UserPort userPort, OrderPort orderPort) {
        this.clinicalRecordPort = clinicalRecordPort;
        this.patientPort = patientPort;
        this.userPort = userPort;
        this.orderPort = orderPort;
    }

    public void createClinicalRecord(ClinicalRecord record) throws BusinessException {
        Patient patient = patientPort.findByDocument(record.getPatient().getDocument());
        if (patient == null) {
            throw new BusinessException("No existe el paciente");
        }
        User doctor = userPort.findByDocument(record.getDoctor().getDocument());
        if (doctor == null) {
            throw new BusinessException("No existe el doctor");
        }
        if (!doctor.getRole().equals(Role.DOCTOR)) {
            throw new BusinessException("El usuario no es un doctor");
        }
        if (record.getOrder() == null) {
            throw new BusinessException("El registro clinico debe tener una orden asociada");
        }
        Order order = orderPort.findById(record.getOrder().getId());
        if (order == null) {
            throw new BusinessException("No existe la orden asociada");
        }
        record.setDate(new Date(System.currentTimeMillis()));
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setOrder(order);
        clinicalRecordPort.save(record);
    }
}
