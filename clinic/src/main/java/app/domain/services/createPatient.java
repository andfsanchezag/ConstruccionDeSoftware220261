package app.domain.services;

import app.domain.models.Patient;
import app.domain.ports.ContactEmergencyPort;
import app.domain.ports.PatientPort;
import app.domain.Exceptions.BusinessException;

public class CreatePatient {

    private PatientPort patientPort;
    private ContactEmergencyPort emergencyContactPort;
    private CreateEmergencyContact createEmergencyContact;

    public void createPatient(Patient patient) throws BusinessException {
        // Validar que el paciente no exista en la base de datos
        // Guardar el paciente en la base de datos
        if (patientPort.existsByDocument(patient.getDocument())) {
            throw new BusinessException("Ya existe un paciente con esa cedula");
        }
        if(!emergencyContactPort.existsByDocument(patient.getEmergencyContact().getDocument())) {
            createEmergencyContact.createEmergencyContact(patient.getEmergencyContact());
        }
        patientPort.save(patient);

    }
    
}
