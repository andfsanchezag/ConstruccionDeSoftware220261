package app.domain.ports;

import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.patient.Patient;
import java.util.List;

public interface ClinicalRecordPort {

    void save(ClinicalRecord clinicalRecord);
    List<ClinicalRecord> findByPatient(Patient patient);

}
