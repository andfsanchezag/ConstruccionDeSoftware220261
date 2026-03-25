package app.application.adapters.persistence.sql;

import app.domain.models.enums.Gender;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.patient.Company;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import app.domain.ports.PatientPort;
import app.application.adapters.persistence.sql.entities.CompanyEntity;
import app.application.adapters.persistence.sql.entities.EmergencyContactEntity;
import app.application.adapters.persistence.sql.entities.PatientEntity;
import app.application.adapters.persistence.sql.entities.PolicyEntity;
import app.application.adapters.persistence.sql.repositories.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientPersistenceAdapter implements PatientPort {

    private final PatientRepository repository;

    public PatientPersistenceAdapter(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsByDocument(String document) {
        return repository.existsByDocument(document);
    }

    @Override
    public void save(Patient patient) {
        repository.save(toEntity(patient));
    }

    @Override
    public void update(Patient patient) {
        PatientEntity existing = repository.findByDocument(patient.getDocument());
        if (existing != null) {
            existing.setName(patient.getName());
            existing.setPhone(patient.getPhone());
            existing.setEmail(patient.getEmail());
            existing.setAddress(patient.getAddress());
            existing.setBirthDate(patient.getBirthDate());
            existing.setGender(patient.getGender() != null ? patient.getGender().toString() : null);
            repository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteByDocument(String document) {
        repository.deleteByDocument(document);
    }

    @Override
    public Patient findByDocument(String document) {
        return toModel(repository.findByDocument(document));
    }

    @Override
    public List<Patient> findAll() {
        return repository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    private PatientEntity toEntity(Patient patient) {
        PatientEntity e = new PatientEntity();
        e.setName(patient.getName());
        e.setDocument(patient.getDocument());
        e.setPhone(patient.getPhone());
        e.setEmail(patient.getEmail());
        e.setAddress(patient.getAddress());
        e.setBirthDate(patient.getBirthDate());
        e.setGender(patient.getGender() != null ? patient.getGender().toString() : null);
        if (patient.getEmergencyContact() != null) {
            EmergencyContactEntity ec = new EmergencyContactEntity();
            ec.setName(patient.getEmergencyContact().getName());
            ec.setDocument(patient.getEmergencyContact().getDocument());
            ec.setPhone(patient.getEmergencyContact().getPhone());
            ec.setEmail(patient.getEmergencyContact().getEmail());
            ec.setAddress(patient.getEmergencyContact().getAddress());
            ec.setBirthDate(patient.getEmergencyContact().getBirthDate());
            ec.setRelationship(patient.getEmergencyContact().getRelationship());
            e.setEmergencyContact(ec);
        }
        if (patient.getPolicy() != null) {
            PolicyEntity pe = new PolicyEntity();
            pe.setId(patient.getPolicy().getId());
            pe.setPolicyNumber(patient.getPolicy().getPolicyNumber());
            pe.setActive(patient.getPolicy().isActive());
            pe.setExpiryDate(patient.getPolicy().getExpiryDate());
            if (patient.getPolicy().getCompany() != null) {
                CompanyEntity ce = new CompanyEntity();
                ce.setId(patient.getPolicy().getCompany().getId());
                ce.setName(patient.getPolicy().getCompany().getName());
                pe.setCompany(ce);
            }
            e.setPolicy(pe);
        }
        return e;
    }

    private Patient toModel(PatientEntity e) {
        if (e == null) return null;
        Patient patient = new Patient();
        patient.setId(e.getId());
        patient.setName(e.getName());
        patient.setDocument(e.getDocument());
        patient.setPhone(e.getPhone());
        patient.setEmail(e.getEmail());
        patient.setAddress(e.getAddress());
        patient.setBirthDate(e.getBirthDate());
        patient.setGender(e.getGender() != null ? Gender.valueOf(e.getGender()) : null);
        if (e.getEmergencyContact() != null) {
            EmergencyContact ec = new EmergencyContact();
            ec.setId(e.getEmergencyContact().getId());
            ec.setName(e.getEmergencyContact().getName());
            ec.setDocument(e.getEmergencyContact().getDocument());
            ec.setPhone(e.getEmergencyContact().getPhone());
            ec.setEmail(e.getEmergencyContact().getEmail());
            ec.setAddress(e.getEmergencyContact().getAddress());
            ec.setBirthDate(e.getEmergencyContact().getBirthDate());
            ec.setRelationship(e.getEmergencyContact().getRelationship());
            patient.setEmergencyContact(ec);
        }
        if (e.getPolicy() != null) {
            Policy policy = new Policy();
            policy.setId(e.getPolicy().getId());
            policy.setPolicyNumber(e.getPolicy().getPolicyNumber());
            policy.setActive(e.getPolicy().isActive());
            policy.setExpiryDate(e.getPolicy().getExpiryDate());
            if (e.getPolicy().getCompany() != null) {
                Company company = new Company();
                company.setId(e.getPolicy().getCompany().getId());
                company.setName(e.getPolicy().getCompany().getName());
                policy.setCompany(company);
            }
            patient.setPolicy(policy);
        }
        return patient;
    }
}
