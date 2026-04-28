package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.services.CreateClinicalRecord;
import app.domain.services.CreateOrder;
import app.domain.services.FindClinicalRecord;
import app.domain.services.FindOrder;
import app.domain.services.FindPatient;

import java.util.List;

@Service
public class DoctorUseCase implements app.domain.ports.in.DoctorUseCase {

    @Autowired
    private FindPatient findPatient;
    @Autowired
    private CreateOrder createOrder;
    @Autowired
    private FindOrder findOrder;
    @Autowired
    private CreateClinicalRecord createClinicalRecord;
    @Autowired
    private FindClinicalRecord findClinicalRecord;

    public DoctorUseCase(FindPatient findPatient, CreateOrder createOrder, FindOrder findOrder,
                          CreateClinicalRecord createClinicalRecord, FindClinicalRecord findClinicalRecord) {
        this.findPatient = findPatient;
        this.createOrder = createOrder;
        this.findOrder = findOrder;
        this.createClinicalRecord = createClinicalRecord;
        this.findClinicalRecord = findClinicalRecord;
    }

    @Override
    public Patient findPatientByDocument(String document) throws BusinessException {
        return findPatient.findByDocument(document);
    }

    @Override
    public List<Patient> findAllPatients() {
        return findPatient.findAll();
    }

    @Override
    public void createOrder(Order order) throws BusinessException {
        createOrder.createOrder(order);
    }

    @Override
    public Order findOrderById(long id) throws BusinessException {
        return findOrder.findById(id);
    }
    @Override
    public List<Order> findOrdersByPatient(String patientDocument) throws BusinessException {
        return findOrder.findByPatient(patientDocument);
    }

    @Override
    public void createClinicalRecord(ClinicalRecord record) throws BusinessException {
        createClinicalRecord.createClinicalRecord(record);
    }

    @Override
    public List<ClinicalRecord> findClinicalRecordsByPatient(String patientDocument) throws BusinessException {
        return findClinicalRecord.findByPatient(patientDocument);
    }

}
