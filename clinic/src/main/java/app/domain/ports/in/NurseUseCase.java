package app.domain.ports.in;

import app.domain.Exceptions.BusinessException;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.clinic.ClinicalVisit;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;

import java.util.List;

public interface NurseUseCase {

    Patient findPatientByDocument(String document) throws BusinessException;

    List<Patient> findAllPatients();

    Order findOrderById(long id) throws BusinessException;

    List<Order> findOrdersByPatient(String patientDocument) throws BusinessException;

    void createClinicalVisit(ClinicalVisit visit) throws BusinessException;

    List<ClinicalRecord> findClinicalRecordsByPatient(String patientDocument) throws BusinessException;

}
