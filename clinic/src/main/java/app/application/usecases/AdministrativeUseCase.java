package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.billing.Invoice;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import app.domain.services.CreateEmergencyContact;
import app.domain.services.CreateInvoice;
import app.domain.services.CreateOrder;
import app.domain.services.CreatePatient;
import app.domain.services.CreatePolicy;
import app.domain.services.DeletePatient;
import app.domain.services.FindInvoice;
import app.domain.services.FindOrder;
import app.domain.services.FindPatient;
import app.domain.services.UpdateEmergencyContact;
import app.domain.services.UpdatePatient;
import app.domain.services.UpdatePolicy;

import java.util.List;

@Service
public class AdministrativeUseCase implements app.domain.ports.in.AdministrativeUseCase {

    @Autowired
    private CreatePatient createPatient;
    @Autowired
    private UpdatePatient updatePatient;
    @Autowired
    private DeletePatient deletePatient;
    @Autowired
    private FindPatient findPatient;
    @Autowired
    private CreateEmergencyContact createEmergencyContact;
    @Autowired
    private UpdateEmergencyContact updateEmergencyContact;
    @Autowired
    private CreatePolicy createPolicy;
    @Autowired
    private UpdatePolicy updatePolicy;
    @Autowired
    private CreateOrder createOrder;
    @Autowired
    private FindOrder findOrder;
    @Autowired
    private CreateInvoice createInvoice;
    @Autowired
    private FindInvoice findInvoice;

    public AdministrativeUseCase(CreatePatient createPatient, UpdatePatient updatePatient,
                                  DeletePatient deletePatient, FindPatient findPatient,
                                  CreateEmergencyContact createEmergencyContact,
                                  UpdateEmergencyContact updateEmergencyContact,
                                  CreatePolicy createPolicy, UpdatePolicy updatePolicy,
                                  CreateOrder createOrder, FindOrder findOrder,
                                  CreateInvoice createInvoice, FindInvoice findInvoice) {
        this.createPatient = createPatient;
        this.updatePatient = updatePatient;
        this.deletePatient = deletePatient;
        this.findPatient = findPatient;
        this.createEmergencyContact = createEmergencyContact;
        this.updateEmergencyContact = updateEmergencyContact;
        this.createPolicy = createPolicy;
        this.updatePolicy = updatePolicy;
        this.createOrder = createOrder;
        this.findOrder = findOrder;
        this.createInvoice = createInvoice;
        this.findInvoice = findInvoice;
    }

    @Override
    public void createPatient(Patient patient) throws BusinessException {
        createPatient.createPatient(patient);
    }

    @Override
    public void updatePatient(Patient patient) throws BusinessException {
        updatePatient.updatePatient(patient);
    }

    @Override
    public void deletePatient(String document) throws BusinessException {
        deletePatient.deletePatient(document);
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
    public void createEmergencyContact(EmergencyContact emergencyContact) throws BusinessException {
        createEmergencyContact.createEmergencyContact(emergencyContact);
    }

    @Override
    public void updateEmergencyContact(String patientDocument, EmergencyContact emergencyContact) throws BusinessException {
        updateEmergencyContact.updateEmergencyContact(patientDocument, emergencyContact);
    }

    @Override
    public void createPolicy(String patientDocument, Policy policy) throws BusinessException {
        createPolicy.createPolicy(patientDocument, policy);
    }

    @Override
    public void updatePolicy(Policy policy) throws BusinessException {
        updatePolicy.updatePolicy(policy);
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
    public void createInvoice(Invoice invoice) throws BusinessException {
        createInvoice.createInvoice(invoice);
    }

    @Override
    public Invoice findInvoiceById(long id) throws BusinessException {
        return findInvoice.findById(id);
    }

    @Override
    public List<Invoice> findInvoicesByPatient(String patientDocument) throws BusinessException {
        return findInvoice.findByPatient(patientDocument);
    }

}
