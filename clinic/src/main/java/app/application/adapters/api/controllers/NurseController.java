package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.ClinicalVisitRequest;
import app.application.adapters.api.response.ClinicalRecordResponse;
import app.application.adapters.api.response.ClinicalVisitResponse;
import app.application.adapters.api.response.EmergencyContactResponse;
import app.application.adapters.api.response.OrderItemResponse;
import app.application.adapters.api.response.OrderResponse;
import app.application.adapters.api.response.PatientResponse;
import app.application.adapters.api.response.PolicyResponse;
import app.application.usecases.NurseUseCase;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.clinic.ClinicalVisit;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import app.domain.models.identity.User;

import java.util.List;

@RestController
@RequestMapping("/nurse")
public class NurseController {

    @Autowired
    private NurseUseCase nurseUseCase;

    public NurseController(NurseUseCase nurseUseCase) {
        this.nurseUseCase = nurseUseCase;
    }

    // ── Patients ──────────────────────────────────────────────────────────────

    @GetMapping("/patients/{document}")
    public ResponseEntity<PatientResponse> findPatient(@PathVariable String document) {
        Patient patient = nurseUseCase.findPatientByDocument(document);
        return ResponseEntity.ok(toPatientResponse(patient));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponse>> findAllPatients() {
        List<PatientResponse> patients = nurseUseCase.findAllPatients()
                .stream().map(NurseController::toPatientResponse).toList();
        return ResponseEntity.ok(patients);
    }

    // ── Orders ────────────────────────────────────────────────────────────────

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable long id) {
        Order order = nurseUseCase.findOrderById(id);
        return ResponseEntity.ok(toOrderResponse(order));
    }

    @GetMapping("/patients/{patientDocument}/orders")
    public ResponseEntity<List<OrderResponse>> findOrdersByPatient(@PathVariable String patientDocument) {
        List<OrderResponse> orders = nurseUseCase.findOrdersByPatient(patientDocument)
                .stream().map(NurseController::toOrderResponse).toList();
        return ResponseEntity.ok(orders);
    }

    // ── Clinical Visits ───────────────────────────────────────────────────────

    @PostMapping("/clinical-visits")
    public ResponseEntity<ClinicalVisitResponse> createClinicalVisit(
            @Valid @RequestBody ClinicalVisitRequest request,
            Authentication authentication) {
        String nurseDocument = (String) authentication.getDetails();
        ClinicalVisit visit = toClinicalVisit(request, nurseDocument);
        nurseUseCase.createClinicalVisit(visit);
        return ResponseEntity.status(HttpStatus.CREATED).body(toClinicalVisitResponse(visit));
    }

    // ── Clinical Records (read-only) ──────────────────────────────────────────

    @GetMapping("/patients/{patientDocument}/clinical-records")
    public ResponseEntity<List<ClinicalRecordResponse>> findClinicalRecordsByPatient(
            @PathVariable String patientDocument) {
        List<ClinicalRecordResponse> records = nurseUseCase.findClinicalRecordsByPatient(patientDocument)
                .stream().map(NurseController::toClinicalRecordResponse).toList();
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

    private static ClinicalVisit toClinicalVisit(ClinicalVisitRequest req, String nurseDocument) {
        ClinicalVisit visit = new ClinicalVisit();
        Patient patient = new Patient();
        patient.setDocument(req.getPatientDocument());
        visit.setPatient(patient);
        User nurse = new User();
        nurse.setDocument(nurseDocument);
        visit.setNurse(nurse);
        visit.setBloodPressure(req.getBloodPressure());
        visit.setTemperature(req.getTemperature());
        visit.setPulse(req.getPulse());
        visit.setOxygenLevel(req.getOxygenLevel());
        visit.setObservations(req.getObservations());
        if (req.getOrderId() != 0) {
            Order order = new Order();
            order.setId(req.getOrderId());
            visit.setOrder(order);
        }
        return visit;
    }

    private static ClinicalVisitResponse toClinicalVisitResponse(ClinicalVisit visit) {
        String patDoc = visit.getPatient() != null ? visit.getPatient().getDocument() : null;
        String patName = visit.getPatient() != null ? visit.getPatient().getName() : null;
        String nurseDoc = visit.getNurse() != null ? visit.getNurse().getDocument() : null;
        String nurseName = visit.getNurse() != null ? visit.getNurse().getName() : null;
        long orderId = visit.getOrder() != null ? visit.getOrder().getId() : 0;
        return new ClinicalVisitResponse(
                visit.getId(), patDoc, patName, nurseDoc, nurseName,
                visit.getDate(), visit.getBloodPressure(), visit.getTemperature(),
                visit.getPulse(), visit.getOxygenLevel(), visit.getObservations(), orderId
        );
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
