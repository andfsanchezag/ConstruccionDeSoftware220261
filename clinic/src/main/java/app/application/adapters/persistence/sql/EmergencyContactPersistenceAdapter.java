package app.application.adapters.persistence.sql;

import app.domain.models.identity.EmergencyContact;
import app.domain.ports.out.ContactEmergencyPort;
import app.application.adapters.persistence.sql.entities.EmergencyContactEntity;
import app.application.adapters.persistence.sql.repositories.EmergencyContactRepository;
import org.springframework.stereotype.Service;

@Service
public class EmergencyContactPersistenceAdapter implements ContactEmergencyPort {

    private final EmergencyContactRepository repository;

    public EmergencyContactPersistenceAdapter(EmergencyContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByDocument(String document) {
        return repository.existsByDocument(document);
    }

    @Override
    public void save(EmergencyContact contact) {
        repository.save(toEntity(contact));
    }

    @Override
    public void update(EmergencyContact contact) {
        EmergencyContactEntity existing = repository.findByDocument(contact.getDocument());
        if (existing != null) {
            existing.setName(contact.getName());
            existing.setPhone(contact.getPhone());
            existing.setEmail(contact.getEmail());
            existing.setAddress(contact.getAddress());
            existing.setBirthDate(contact.getBirthDate());
            existing.setRelationship(contact.getRelationship());
            repository.save(existing);
        }
    }

    @Override
    public EmergencyContact findByDocument(String document) {
        return toModel(repository.findByDocument(document));
    }

    private EmergencyContactEntity toEntity(EmergencyContact contact) {
        EmergencyContactEntity e = new EmergencyContactEntity();
        e.setName(contact.getName());
        e.setDocument(contact.getDocument());
        e.setPhone(contact.getPhone());
        e.setEmail(contact.getEmail());
        e.setAddress(contact.getAddress());
        e.setBirthDate(contact.getBirthDate());
        e.setRelationship(contact.getRelationship());
        return e;
    }

    private EmergencyContact toModel(EmergencyContactEntity e) {
        if (e == null) return null;
        EmergencyContact contact = new EmergencyContact();
        contact.setId(e.getId());
        contact.setName(e.getName());
        contact.setDocument(e.getDocument());
        contact.setPhone(e.getPhone());
        contact.setEmail(e.getEmail());
        contact.setAddress(e.getAddress());
        contact.setBirthDate(e.getBirthDate());
        contact.setRelationship(e.getRelationship());
        return contact;
    }
}
