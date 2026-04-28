package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.patient.Patient;
import app.domain.ports.out.ContactEmergencyPort;
import app.domain.ports.out.PatientPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePatient {

    private PatientPort patientPort;
    private ContactEmergencyPort emergencyContactPort;
    private CreateEmergencyContact createEmergencyContact;

    @Autowired
    public CreatePatient(PatientPort patientPort, ContactEmergencyPort emergencyContactPort,
                         CreateEmergencyContact createEmergencyContact) {
        this.patientPort = patientPort;
        this.emergencyContactPort = emergencyContactPort;
        this.createEmergencyContact = createEmergencyContact;
    }

    public void createPatient(Patient patient) throws BusinessException {
        if (patientPort.existsByDocument(patient.getDocument())) {
            throw new BusinessException("Ya existe un paciente con esa cedula");
        }
        if (!emergencyContactPort.existsByDocument(patient.getEmergencyContact().getDocument())) {
            createEmergencyContact.createEmergencyContact(patient.getEmergencyContact());
        }
        patientPort.save(patient);
    }
}

