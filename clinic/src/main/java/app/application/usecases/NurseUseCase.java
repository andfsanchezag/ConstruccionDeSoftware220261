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
public class NurseUseCase implements app.domain.ports.in.NurseUseCase {

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

    @Override
    public Patient findPatientByDocument(String document) throws BusinessException {
        return findPatient.findByDocument(document);
    }

    @Override
    public List<Patient> findAllPatients() {
        return findPatient.findAll();
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
    public void createClinicalVisit(ClinicalVisit visit) throws BusinessException {
        createClinicalVisit.createClinicalVisit(visit);
    }

    @Override
    public List<ClinicalRecord> findClinicalRecordsByPatient(String patientDocument) throws BusinessException {
        return findClinicalRecord.findByPatient(patientDocument);
    }

}
