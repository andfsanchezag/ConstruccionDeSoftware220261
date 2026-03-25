package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.patient.Patient;
import app.domain.ports.PatientPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdatePatient {

    private PatientPort patientPort;

    @Autowired
    public UpdatePatient(PatientPort patientPort) {
        this.patientPort = patientPort;
    }

    public void updatePatient(Patient patient) throws BusinessException {
        if (!patientPort.existsByDocument(patient.getDocument())) {
            throw new BusinessException("No existe un paciente con esa cedula");
        }
        patientPort.update(patient);
    }
}
