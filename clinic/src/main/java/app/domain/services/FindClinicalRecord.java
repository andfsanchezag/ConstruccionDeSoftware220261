package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.patient.Patient;
import app.domain.ports.ClinicalRecordPort;
import app.domain.ports.PatientPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FindClinicalRecord {

    private ClinicalRecordPort clinicalRecordPort;
    private PatientPort patientPort;

    @Autowired
    public FindClinicalRecord(ClinicalRecordPort clinicalRecordPort, PatientPort patientPort) {
        this.clinicalRecordPort = clinicalRecordPort;
        this.patientPort = patientPort;
    }

    public List<ClinicalRecord> findByPatient(String patientDocument) throws BusinessException {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new BusinessException("No existe un paciente con esa cedula");
        }
        return clinicalRecordPort.findByPatient(patient);
    }
}
