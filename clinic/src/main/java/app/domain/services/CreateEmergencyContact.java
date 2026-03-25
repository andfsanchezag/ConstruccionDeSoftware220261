package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.identity.EmergencyContact;
import app.domain.ports.ContactEmergencyPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateEmergencyContact {

    private ContactEmergencyPort emergencyContactPort;

    @Autowired
    public CreateEmergencyContact(ContactEmergencyPort emergencyContactPort) {
        this.emergencyContactPort = emergencyContactPort;
    }

    public void createEmergencyContact(EmergencyContact emergencyContact) throws BusinessException {
        if (emergencyContactPort.existsByDocument(emergencyContact.getDocument())) {
            throw new BusinessException("Ya existe un contacto de emergencia con esa cedula");
        }
        emergencyContactPort.save(emergencyContact);
    }
}
