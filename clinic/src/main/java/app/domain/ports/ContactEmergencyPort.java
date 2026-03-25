package app.domain.ports;

import app.domain.models.identity.EmergencyContact;

public interface ContactEmergencyPort {

    boolean existsByDocument(String document);
    void save(EmergencyContact emergencyContact);
    void update(EmergencyContact emergencyContact);
    EmergencyContact findByDocument(String document);

}
