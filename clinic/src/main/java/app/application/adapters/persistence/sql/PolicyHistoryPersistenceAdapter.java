package app.application.adapters.persistence.sql;

import app.domain.models.patient.Patient;
import app.domain.models.patient.PolicyHistory;
import app.domain.ports.out.PolicyHistoryPort;
import app.application.adapters.persistence.sql.entities.PatientEntity;
import app.application.adapters.persistence.sql.entities.PolicyHistoryEntity;
import app.application.adapters.persistence.sql.repositories.PolicyHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class PolicyHistoryPersistenceAdapter implements PolicyHistoryPort {

    private final PolicyHistoryRepository repository;

    public PolicyHistoryPersistenceAdapter(PolicyHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public PolicyHistory findByPatientAndYear(Patient patient, int year) {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patient.getId());
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
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(ph.getPatient().getId());
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
