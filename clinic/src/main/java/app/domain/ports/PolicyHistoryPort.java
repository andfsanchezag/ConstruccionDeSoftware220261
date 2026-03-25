package app.domain.ports;

import app.domain.models.patient.Patient;
import app.domain.models.patient.PolicyHistory;

public interface PolicyHistoryPort {

    PolicyHistory findByPatientAndYear(Patient patient, int year);
    void save(PolicyHistory policyHistory);
    void update(PolicyHistory policyHistory);

}
