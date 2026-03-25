package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.ports.PatientPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletePatient {

    private PatientPort patientPort;

    @Autowired
    public DeletePatient(PatientPort patientPort) {
        this.patientPort = patientPort;
    }

    public void deletePatient(String document) throws BusinessException {
        if (!patientPort.existsByDocument(document)) {
            throw new BusinessException("No existe un paciente con esa cedula");
        }
        patientPort.deleteByDocument(document);
    }
}
