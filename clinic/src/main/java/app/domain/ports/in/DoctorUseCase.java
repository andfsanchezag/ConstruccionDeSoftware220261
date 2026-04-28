package app.domain.ports.in;

import app.domain.Exceptions.BusinessException;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.patient.Patient;
import app.domain.models.orders.Order;
import java.util.List;


public interface DoctorUseCase {

    public Patient findPatientByDocument(String document) throws BusinessException;

    public List<Patient> findAllPatients() ;

    public void createOrder(Order order) throws BusinessException ;

    public Order findOrderById(long id) throws BusinessException ;

    public List<Order> findOrdersByPatient(String patientDocument) throws BusinessException ;

    public void createClinicalRecord(ClinicalRecord record) throws BusinessException;

    public List<ClinicalRecord> findClinicalRecordsByPatient(String patientDocument) throws BusinessException;
    
}
