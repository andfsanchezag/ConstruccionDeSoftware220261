package app.domain.ports;

import app.domain.models.EmergencyContact;

public interface ContactEmergencyPort {
    public boolean existsByDocument(String cedula);
    public void save(EmergencyContact emergencyContact);
    
}
