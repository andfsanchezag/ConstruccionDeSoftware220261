package app.application.adapters.persistence.mongodb.repositories;

import app.application.adapters.persistence.mongodb.documents.ClinicalRecordDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClinicalRecordMongoRepository extends MongoRepository<ClinicalRecordDocument, String> {
    Optional<ClinicalRecordDocument> findByPatientDocument(String patientDocument);
}
