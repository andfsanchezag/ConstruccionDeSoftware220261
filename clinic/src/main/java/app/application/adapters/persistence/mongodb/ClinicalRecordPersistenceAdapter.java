package app.application.adapters.persistence.mongodb;

import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.identity.User;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.ports.out.ClinicalRecordPort;
import app.application.adapters.persistence.mongodb.documents.ClinicalRecordDocument;
import app.application.adapters.persistence.mongodb.documents.ClinicalRecordDocument.RecordEntry;
import app.application.adapters.persistence.mongodb.repositories.ClinicalRecordMongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicalRecordPersistenceAdapter implements ClinicalRecordPort {

    private final ClinicalRecordMongoRepository repository;

    public ClinicalRecordPersistenceAdapter(ClinicalRecordMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ClinicalRecord clinicalRecord) {
        String patientDoc = clinicalRecord.getPatient().getDocument();
        String dateKey = clinicalRecord.getDate() != null
                ? clinicalRecord.getDate().toLocalDate().toString()
                : LocalDate.now().toString();

        ClinicalRecordDocument document = repository.findByPatientDocument(patientDoc)
                .orElseGet(() -> {
                    ClinicalRecordDocument newDoc = new ClinicalRecordDocument();
                    newDoc.setPatientDocument(patientDoc);
                    newDoc.setRecords(new HashMap<>());
                    return newDoc;
                });

        RecordEntry entry = new RecordEntry();
        entry.setReason(clinicalRecord.getReason());
        entry.setSymptoms(clinicalRecord.getSymptoms());
        entry.setDiagnosis(clinicalRecord.getDiagnosis());
        if (clinicalRecord.getDoctor() != null) {
            entry.setDoctorDocument(clinicalRecord.getDoctor().getDocument());
            entry.setDoctorName(clinicalRecord.getDoctor().getName());
        }
        if (clinicalRecord.getOrder() != null) {
            entry.setOrderId(clinicalRecord.getOrder().getId());
        }

        document.getRecords().computeIfAbsent(dateKey, k -> new ArrayList<>()).add(entry);
        repository.save(document);
    }

    @Override
    public List<ClinicalRecord> findByPatient(Patient patient) {
        return repository.findByPatientDocument(patient.getDocument())
                .map(doc -> doc.getRecords().entrySet().stream()
                        .flatMap(entry -> entry.getValue().stream().map(re -> toModel(doc.getPatientDocument(), entry.getKey(), re, patient)))
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    private ClinicalRecord toModel(String patientDocument, String dateKey, RecordEntry re, Patient patient) {
        ClinicalRecord cr = new ClinicalRecord();
        cr.setPatient(patient);
        cr.setReason(re.getReason());
        cr.setSymptoms(re.getSymptoms());
        cr.setDiagnosis(re.getDiagnosis());
        try {
            LocalDate localDate = LocalDate.parse(dateKey);
            cr.setDate(java.sql.Date.valueOf(localDate));
        } catch (Exception ignored) {
        }
        if (re.getDoctorDocument() != null) {
            User doctor = new User();
            doctor.setDocument(re.getDoctorDocument());
            doctor.setName(re.getDoctorName());
            cr.setDoctor(doctor);
        }
        if (re.getOrderId() != null) {
            Order order = new Order();
            order.setId(re.getOrderId());
            cr.setOrder(order);
        }
        return cr;
    }
}
