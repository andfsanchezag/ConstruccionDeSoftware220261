package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.clinic.ClinicalVisit;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.services.CreateClinicalVisit;
import app.domain.services.FindClinicalRecord;
import app.domain.services.FindOrder;
import app.domain.services.FindPatient;

import java.util.List;

@Service
public class NurseUseCase {

    @Autowired
    private FindPatient findPatient;
    @Autowired
    private FindOrder findOrder;
    @Autowired
    private CreateClinicalVisit createClinicalVisit;
    @Autowired
    private FindClinicalRecord findClinicalRecord;

    public NurseUseCase(FindPatient findPatient, FindOrder findOrder,
                         CreateClinicalVisit createClinicalVisit, FindClinicalRecord findClinicalRecord) {
        this.findPatient = findPatient;
        this.findOrder = findOrder;
        this.createClinicalVisit = createClinicalVisit;
        this.findClinicalRecord = findClinicalRecord;
    }

    public Patient findPatientByDocument(String document) throws BusinessException {
        return findPatient.findByDocument(document);
    }

    public List<Patient> findAllPatients() {
        return findPatient.findAll();
    }

    public Order findOrderById(long id) throws BusinessException {
        return findOrder.findById(id);
    }

    public List<Order> findOrdersByPatient(String patientDocument) throws BusinessException {
        return findOrder.findByPatient(patientDocument);
    }

    public void createClinicalVisit(ClinicalVisit visit) throws BusinessException {
        createClinicalVisit.createClinicalVisit(visit);
    }

    public List<ClinicalRecord> findClinicalRecordsByPatient(String patientDocument) throws BusinessException {
        return findClinicalRecord.findByPatient(patientDocument);
    }

}
