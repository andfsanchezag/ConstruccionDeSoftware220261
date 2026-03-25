package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.*;
import app.application.adapters.api.response.*;
import app.application.usecases.AdministrativeUseCase;
import app.domain.models.billing.Invoice;
import app.domain.models.billing.InvoiceItem;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.inventory.Medicine;
import app.domain.models.orders.Order;
import app.domain.models.orders.OrderItem;
import app.domain.models.patient.Company;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import app.domain.models.identity.User;

import java.util.List;

@RestController
@RequestMapping("/administrative")
public class AdministrativeController {

    @Autowired
    private AdministrativeUseCase administrativeUseCase;

    public AdministrativeController(AdministrativeUseCase administrativeUseCase) {
        this.administrativeUseCase = administrativeUseCase;
    }

    // ── Patients ──────────────────────────────────────────────────────────────

    @PostMapping("/patients")
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest request) {
        Patient patient = toPatient(request);
        administrativeUseCase.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(toPatientResponse(patient));
    }

    @PutMapping("/patients/{document}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable String document,
                                                          @Valid @RequestBody PatientRequest request) {
        request.setDocument(document);
        Patient patient = toPatient(request);
        administrativeUseCase.updatePatient(patient);
        return ResponseEntity.ok(toPatientResponse(patient));
    }

    @DeleteMapping("/patients/{document}")
    public ResponseEntity<Void> deletePatient(@PathVariable String document) {
        administrativeUseCase.deletePatient(document);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients/{document}")
    public ResponseEntity<PatientResponse> findPatient(@PathVariable String document) {
        Patient patient = administrativeUseCase.findPatientByDocument(document);
        return ResponseEntity.ok(toPatientResponse(patient));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponse>> findAllPatients() {
        List<PatientResponse> patients = administrativeUseCase.findAllPatients()
                .stream().map(AdministrativeController::toPatientResponse).toList();
        return ResponseEntity.ok(patients);
    }

    // ── Emergency Contacts ────────────────────────────────────────────────────

    @PostMapping("/emergency-contacts")
    public ResponseEntity<EmergencyContactResponse> createEmergencyContact(
            @Valid @RequestBody EmergencyContactRequest request) {
        EmergencyContact ec = toEmergencyContact(request);
        administrativeUseCase.createEmergencyContact(ec);
        return ResponseEntity.status(HttpStatus.CREATED).body(toEmergencyContactResponse(ec));
    }

    @PutMapping("/emergency-contacts/{patientDocument}")
    public ResponseEntity<EmergencyContactResponse> updateEmergencyContact(
            @PathVariable String patientDocument,
            @Valid @RequestBody EmergencyContactRequest request) {
        EmergencyContact ec = toEmergencyContact(request);
        administrativeUseCase.updateEmergencyContact(patientDocument, ec);
        return ResponseEntity.ok(toEmergencyContactResponse(ec));
    }

    // ── Policies ─────────────────────────────────────────────────────────────

    @PostMapping("/patients/{patientDocument}/policies")
    public ResponseEntity<PolicyResponse> createPolicy(@PathVariable String patientDocument,
                                                        @Valid @RequestBody PolicyRequest request) {
        Policy policy = toPolicy(request);
        administrativeUseCase.createPolicy(patientDocument, policy);
        return ResponseEntity.status(HttpStatus.CREATED).body(toPolicyResponse(policy));
    }

    @PutMapping("/policies/{id}")
    public ResponseEntity<PolicyResponse> updatePolicy(@PathVariable long id,
                                                        @Valid @RequestBody PolicyRequest request) {
        request.setId(id);
        Policy policy = toPolicy(request);
        administrativeUseCase.updatePolicy(policy);
        return ResponseEntity.ok(toPolicyResponse(policy));
    }

    // ── Orders ────────────────────────────────────────────────────────────────

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        Order order = toOrder(request);
        administrativeUseCase.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(toOrderResponse(order));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable long id) {
        Order order = administrativeUseCase.findOrderById(id);
        return ResponseEntity.ok(toOrderResponse(order));
    }

    @GetMapping("/patients/{patientDocument}/orders")
    public ResponseEntity<List<OrderResponse>> findOrdersByPatient(@PathVariable String patientDocument) {
        List<OrderResponse> orders = administrativeUseCase.findOrdersByPatient(patientDocument)
                .stream().map(AdministrativeController::toOrderResponse).toList();
        return ResponseEntity.ok(orders);
    }

    // ── Invoices ──────────────────────────────────────────────────────────────

    @PostMapping("/invoices")
    public ResponseEntity<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        Invoice invoice = toInvoice(request);
        administrativeUseCase.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(toInvoiceResponse(invoice));
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<InvoiceResponse> findInvoiceById(@PathVariable long id) {
        Invoice invoice = administrativeUseCase.findInvoiceById(id);
        return ResponseEntity.ok(toInvoiceResponse(invoice));
    }

    @GetMapping("/patients/{patientDocument}/invoices")
    public ResponseEntity<List<InvoiceResponse>> findInvoicesByPatient(@PathVariable String patientDocument) {
        List<InvoiceResponse> invoices = administrativeUseCase.findInvoicesByPatient(patientDocument)
                .stream().map(AdministrativeController::toInvoiceResponse).toList();
        return ResponseEntity.ok(invoices);
    }

    // ── Mappers ───────────────────────────────────────────────────────────────

    private static Patient toPatient(PatientRequest req) {
        Patient patient = new Patient();
        patient.setDocument(req.getDocument());
        patient.setName(req.getName());
        patient.setPhone(req.getPhone());
        patient.setEmail(req.getEmail());
        patient.setAddress(req.getAddress());
        patient.setBirthDate(req.getBirthDate());
        patient.setGender(req.getGender());
        if (req.getEmergencyContact() != null) {
            patient.setEmergencyContact(toEmergencyContact(req.getEmergencyContact()));
        }
        return patient;
    }

    private static PatientResponse toPatientResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getDocument(),
                patient.getName(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getBirthDate(),
                patient.getGender(),
                toEmergencyContactResponse(patient.getEmergencyContact()),
                toPolicyResponse(patient.getPolicy())
        );
    }

    private static EmergencyContact toEmergencyContact(EmergencyContactRequest req) {
        EmergencyContact ec = new EmergencyContact();
        ec.setDocument(req.getDocument());
        ec.setName(req.getName());
        ec.setPhone(req.getPhone());
        ec.setEmail(req.getEmail());
        ec.setAddress(req.getAddress());
        ec.setBirthDate(req.getBirthDate());
        ec.setRelationship(req.getRelationship());
        return ec;
    }

    private static EmergencyContactResponse toEmergencyContactResponse(EmergencyContact ec) {
        if (ec == null) return null;
        return new EmergencyContactResponse(
                ec.getId(), ec.getDocument(), ec.getName(),
                ec.getPhone(), ec.getEmail(), ec.getAddress(),
                ec.getBirthDate(), ec.getRelationship()
        );
    }

    private static Policy toPolicy(PolicyRequest req) {
        Policy policy = new Policy();
        policy.setId(req.getId());
        policy.setPolicyNumber(req.getPolicyNumber());
        policy.setActive(req.isActive());
        policy.setExpiryDate(req.getExpiryDate());
        if (req.getCompanyId() != 0 || req.getCompanyName() != null) {
            Company company = new Company();
            company.setId(req.getCompanyId());
            company.setName(req.getCompanyName());
            policy.setCompany(company);
        }
        return policy;
    }

    private static PolicyResponse toPolicyResponse(Policy policy) {
        if (policy == null) return null;
        long companyId = policy.getCompany() != null ? policy.getCompany().getId() : 0;
        String companyName = policy.getCompany() != null ? policy.getCompany().getName() : null;
        return new PolicyResponse(
                policy.getId(), policy.getPolicyNumber(), policy.isActive(),
                policy.getExpiryDate(), companyId, companyName
        );
    }

    private static Order toOrder(OrderRequest req) {
        Order order = new Order();
        Patient patient = new Patient();
        patient.setDocument(req.getPatientDocument());
        order.setPatient(patient);
        User doctor = new User();
        doctor.setDocument(req.getDoctorDocument());
        order.setDoctor(doctor);
        order.setOrderItems(req.getOrderItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            Medicine medicine = new Medicine();
            medicine.setId(item.getInventoryItemId());
            orderItem.setInventoryItem(medicine);
            orderItem.setItemType(item.getItemType());
            return orderItem;
        }).toList());
        return order;
    }

    private static OrderResponse toOrderResponse(Order order) {
        String patDoc = order.getPatient() != null ? order.getPatient().getDocument() : null;
        String patName = order.getPatient() != null ? order.getPatient().getName() : null;
        String docDoc = order.getDoctor() != null ? order.getDoctor().getDocument() : null;
        String docName = order.getDoctor() != null ? order.getDoctor().getName() : null;
        List<OrderItemResponse> items = order.getOrderItems() == null ? List.of() :
                order.getOrderItems().stream().map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getInventoryItem() != null ? item.getInventoryItem().getId() : 0,
                        item.getInventoryItem() != null ? item.getInventoryItem().getName() : null,
                        item.getInventoryItem() != null ? item.getInventoryItem().getPrice() : 0,
                        item.getItemType()
                )).toList();
        return new OrderResponse(order.getId(), patDoc, patName, docDoc, docName, order.getDate(), items);
    }

    private static Invoice toInvoice(InvoiceRequest req) {
        Invoice invoice = new Invoice();
        Patient patient = new Patient();
        patient.setDocument(req.getPatientDocument());
        invoice.setPatient(patient);
        invoice.setItems(req.getItems().stream().map(item -> {
            InvoiceItem invoiceItem = new InvoiceItem();
            if (item.getOrderId() != 0) {
                Order order = new Order();
                order.setId(item.getOrderId());
                invoiceItem.setOrder(order);
            }
            invoiceItem.setItemType(item.getItemType());
            invoiceItem.setItemName(item.getItemName());
            invoiceItem.setQuantity(item.getQuantity());
            invoiceItem.setUnitPrice(item.getUnitPrice());
            invoiceItem.setTotalPrice(item.getQuantity() * item.getUnitPrice());
            return invoiceItem;
        }).toList());
        return invoice;
    }

    private static InvoiceResponse toInvoiceResponse(Invoice invoice) {
        String patDoc = invoice.getPatient() != null ? invoice.getPatient().getDocument() : null;
        String patName = invoice.getPatient() != null ? invoice.getPatient().getName() : null;
        List<InvoiceItemResponse> items = invoice.getItems() == null ? List.of() :
                invoice.getItems().stream().map(item -> new InvoiceItemResponse(
                        item.getId(),
                        item.getOrder() != null ? item.getOrder().getId() : 0,
                        item.getItemType(),
                        item.getItemName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getTotalPrice()
                )).toList();
        return new InvoiceResponse(
                invoice.getId(), patDoc, patName,
                invoice.getIssueDate(), invoice.getTotalAmount(),
                invoice.getCopayment(), invoice.getInsuranceCoverage(),
                invoice.getPatientPayment(), invoice.isPolicyApplied(), items
        );
    }
}
