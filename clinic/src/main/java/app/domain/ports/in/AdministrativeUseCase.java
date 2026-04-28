package app.domain.ports.in;

import app.domain.Exceptions.BusinessException;
import app.domain.models.billing.Invoice;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;

import java.util.List;

public interface AdministrativeUseCase {

    void createPatient(Patient patient) throws BusinessException;

    void updatePatient(Patient patient) throws BusinessException;

    void deletePatient(String document) throws BusinessException;

    Patient findPatientByDocument(String document) throws BusinessException;

    List<Patient> findAllPatients();

    void createEmergencyContact(EmergencyContact emergencyContact) throws BusinessException;

    void updateEmergencyContact(String patientDocument, EmergencyContact emergencyContact) throws BusinessException;

    void createPolicy(String patientDocument, Policy policy) throws BusinessException;

    void updatePolicy(Policy policy) throws BusinessException;

    void createOrder(Order order) throws BusinessException;

    Order findOrderById(long id) throws BusinessException;

    List<Order> findOrdersByPatient(String patientDocument) throws BusinessException;

    void createInvoice(Invoice invoice) throws BusinessException;

    Invoice findInvoiceById(long id) throws BusinessException;

    List<Invoice> findInvoicesByPatient(String patientDocument) throws BusinessException;

}
