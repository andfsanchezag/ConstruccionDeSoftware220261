package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.patient.Patient;
import app.domain.ports.PatientPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FindPatient {

    private PatientPort patientPort;

    @Autowired
    public FindPatient(PatientPort patientPort) {
        this.patientPort = patientPort;
    }

    public Patient findByDocument(String document) throws BusinessException {
        Patient patient = patientPort.findByDocument(document);
        if (patient == null) {
            throw new BusinessException("No existe un paciente con esa cedula");
        }
        return patient;
    }

    public List<Patient> findAll() {
        return patientPort.findAll();
    }
}
