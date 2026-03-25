package app.application.adapters.persistence.sql;

import app.domain.models.patient.Patient;
import app.domain.models.patient.PolicyHistory;
import app.domain.ports.PolicyHistoryPort;
import app.application.adapters.persistence.sql.entities.PatientEntity;
import app.application.adapters.persistence.sql.entities.PolicyHistoryEntity;
import app.application.adapters.persistence.sql.repositories.PatientRepository;
import app.application.adapters.persistence.sql.repositories.PolicyHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class PolicyHistoryPersistenceAdapter implements PolicyHistoryPort {

    private final PolicyHistoryRepository repository;
    private final PatientRepository patientRepository;

    public PolicyHistoryPersistenceAdapter(PolicyHistoryRepository repository, PatientRepository patientRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
    }

    @Override
    public PolicyHistory findByPatientAndYear(Patient patient, int year) {
        PatientEntity patientEntity = patientRepository.findByDocument(patient.getDocument());
        return repository.findByPatientAndYear(patientEntity, year)
                .map(this::toModel).orElse(null);
    }

    @Override
    public void save(PolicyHistory policyHistory) {
        repository.save(toEntity(policyHistory));
    }

    @Override
    public void update(PolicyHistory policyHistory) {
        repository.findById(policyHistory.getId()).ifPresent(existing -> {
            existing.setYear(policyHistory.getYear());
            existing.setAccumulatedCopayment(policyHistory.getAccumulatedCopayment());
            repository.save(existing);
        });
    }

    private PolicyHistoryEntity toEntity(PolicyHistory ph) {
        PolicyHistoryEntity e = new PolicyHistoryEntity();
        PatientEntity patientEntity = patientRepository.findByDocument(ph.getPatient().getDocument());
        e.setPatient(patientEntity);
        e.setYear(ph.getYear());
        e.setAccumulatedCopayment(ph.getAccumulatedCopayment());
        return e;
    }

    private PolicyHistory toModel(PolicyHistoryEntity e) {
        if (e == null) return null;
        PolicyHistory ph = new PolicyHistory();
        ph.setId(e.getId());
        ph.setYear(e.getYear());
        ph.setAccumulatedCopayment(e.getAccumulatedCopayment());
        return ph;
    }
}
