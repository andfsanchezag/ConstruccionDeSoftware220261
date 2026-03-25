package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.patient.Patient;
import app.domain.ports.ContactEmergencyPort;
import app.domain.ports.PatientPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateEmergencyContact {

    private ContactEmergencyPort emergencyContactPort;
    private PatientPort patientPort;

    @Autowired
    public UpdateEmergencyContact(ContactEmergencyPort emergencyContactPort, PatientPort patientPort) {
        this.emergencyContactPort = emergencyContactPort;
        this.patientPort = patientPort;
    }

    public void updateEmergencyContact(String patientDocument, EmergencyContact emergencyContact) throws BusinessException {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new BusinessException("No existe un paciente con esa cedula");
        }
        if (!emergencyContactPort.existsByDocument(emergencyContact.getDocument())) {
            throw new BusinessException("No existe un contacto de emergencia con esa cedula");
        }
        emergencyContactPort.update(emergencyContact);
        patient.setEmergencyContact(emergencyContact);
        patientPort.update(patient);
    }
}
