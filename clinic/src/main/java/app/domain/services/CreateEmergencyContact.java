package app.domain.services;

import app.domain.models.EmergencyContact;
import app.domain.Exceptions.BusinessException;
import app.domain.ports.ContactEmergencyPort;

public class CreateEmergencyContact {

    private ContactEmergencyPort emergencyContactPort;

    public void createEmergencyContact(EmergencyContact emergencyContact) {
        if (emergencyContactPort.existsByDocument(emergencyContact.getDocument())) {
            throw new BusinessException("Ya existe un contacto de emergencia con esa cedula");
        }
        emergencyContactPort.save(emergencyContact);
    }
    
}
