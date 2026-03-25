package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.ClinicalRecordRequest;
import app.application.adapters.api.request.OrderRequest;
import app.application.adapters.api.response.ClinicalRecordResponse;
import app.application.adapters.api.response.OrderItemResponse;
import app.application.adapters.api.response.OrderResponse;
import app.application.adapters.api.response.PatientResponse;
import app.application.adapters.api.response.EmergencyContactResponse;
import app.application.adapters.api.response.PolicyResponse;
import app.application.usecases.DoctorUseCase;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.inventory.Medicine;
import app.domain.models.orders.Order;
import app.domain.models.orders.OrderItem;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import app.domain.models.identity.User;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorUseCase doctorUseCase;

    public DoctorController(DoctorUseCase doctorUseCase) {
        this.doctorUseCase = doctorUseCase;
    }

    // ── Patients ──────────────────────────────────────────────────────────────

    @GetMapping("/patients/{document}")
    public ResponseEntity<PatientResponse> findPatient(@PathVariable String document) {
        Patient patient = doctorUseCase.findPatientByDocument(document);
        return ResponseEntity.ok(toPatientResponse(patient));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponse>> findAllPatients() {
        List<PatientResponse> patients = doctorUseCase.findAllPatients()
                .stream().map(DoctorController::toPatientResponse).toList();
        return ResponseEntity.ok(patients);
    }

    // ── Orders ────────────────────────────────────────────────────────────────

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        Order order = toOrder(request);
        doctorUseCase.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(toOrderResponse(order));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable long id) {
        Order order = doctorUseCase.findOrderById(id);
        return ResponseEntity.ok(toOrderResponse(order));
    }

    @GetMapping("/patients/{patientDocument}/orders")
    public ResponseEntity<List<OrderResponse>> findOrdersByPatient(@PathVariable String patientDocument) {
        List<OrderResponse> orders = doctorUseCase.findOrdersByPatient(patientDocument)
                .stream().map(DoctorController::toOrderResponse).toList();
        return ResponseEntity.ok(orders);
    }

    // ── Clinical Records ──────────────────────────────────────────────────────

    @PostMapping("/clinical-records")
    public ResponseEntity<ClinicalRecordResponse> createClinicalRecord(
            @Valid @RequestBody ClinicalRecordRequest request) {
        ClinicalRecord record = toClinicalRecord(request);
        doctorUseCase.createClinicalRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(toClinicalRecordResponse(record));
    }

    @GetMapping("/patients/{patientDocument}/clinical-records")
    public ResponseEntity<List<ClinicalRecordResponse>> findClinicalRecordsByPatient(
            @PathVariable String patientDocument) {
        List<ClinicalRecordResponse> records = doctorUseCase.findClinicalRecordsByPatient(patientDocument)
                .stream().map(DoctorController::toClinicalRecordResponse).toList();
        return ResponseEntity.ok(records);
    }

    // ── Mappers ───────────────────────────────────────────────────────────────

    private static PatientResponse toPatientResponse(Patient patient) {
        EmergencyContactResponse ecResponse = null;
        if (patient.getEmergencyContact() != null) {
            EmergencyContact ec = patient.getEmergencyContact();
            ecResponse = new EmergencyContactResponse(
                    ec.getId(), ec.getDocument(), ec.getName(),
                    ec.getPhone(), ec.getEmail(), ec.getAddress(),
                    ec.getBirthDate(), ec.getRelationship()
            );
        }
        PolicyResponse policyResponse = null;
        if (patient.getPolicy() != null) {
            Policy p = patient.getPolicy();
            long companyId = p.getCompany() != null ? p.getCompany().getId() : 0;
            String companyName = p.getCompany() != null ? p.getCompany().getName() : null;
            policyResponse = new PolicyResponse(p.getId(), p.getPolicyNumber(), p.isActive(),
                    p.getExpiryDate(), companyId, companyName);
        }
        return new PatientResponse(
                patient.getId(), patient.getDocument(), patient.getName(),
                patient.getPhone(), patient.getEmail(), patient.getAddress(),
                patient.getBirthDate(), patient.getGender(), ecResponse, policyResponse
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

    private static ClinicalRecord toClinicalRecord(ClinicalRecordRequest req) {
        ClinicalRecord record = new ClinicalRecord();
        Patient patient = new Patient();
        patient.setDocument(req.getPatientDocument());
        record.setPatient(patient);
        User doctor = new User();
        doctor.setDocument(req.getDoctorDocument());
        record.setDoctor(doctor);
        record.setReason(req.getReason());
        record.setSymptoms(req.getSymptoms());
        record.setDiagnosis(req.getDiagnosis());
        if (req.getOrderId() != 0) {
            Order order = new Order();
            order.setId(req.getOrderId());
            record.setOrder(order);
        }
        return record;
    }

    private static ClinicalRecordResponse toClinicalRecordResponse(ClinicalRecord record) {
        String patDoc = record.getPatient() != null ? record.getPatient().getDocument() : null;
        String patName = record.getPatient() != null ? record.getPatient().getName() : null;
        String docDoc = record.getDoctor() != null ? record.getDoctor().getDocument() : null;
        String docName = record.getDoctor() != null ? record.getDoctor().getName() : null;
        long orderId = record.getOrder() != null ? record.getOrder().getId() : 0;
        return new ClinicalRecordResponse(
                patDoc, patName, docDoc, docName,
                record.getDate(), record.getReason(),
                record.getSymptoms(), record.getDiagnosis(), orderId
        );
    }
}
