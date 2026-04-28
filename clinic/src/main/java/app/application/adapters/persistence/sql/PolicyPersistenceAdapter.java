package app.application.adapters.persistence.sql;

import app.domain.models.patient.Company;
import app.domain.models.patient.Policy;
import app.domain.ports.out.PolicyPort;
import app.application.adapters.persistence.sql.entities.CompanyEntity;
import app.application.adapters.persistence.sql.entities.PolicyEntity;
import app.application.adapters.persistence.sql.repositories.PolicyRepository;
import org.springframework.stereotype.Service;

@Service
public class PolicyPersistenceAdapter implements PolicyPort {

    private final PolicyRepository repository;

    public PolicyPersistenceAdapter(PolicyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Policy policy) {
        repository.save(toEntity(policy));
    }

    @Override
    public void update(Policy policy) {
        repository.findById(policy.getId()).ifPresent(existing -> {
            existing.setPolicyNumber(policy.getPolicyNumber());
            existing.setActive(policy.isActive());
            existing.setExpiryDate(policy.getExpiryDate());
            repository.save(existing);
        });
    }

    @Override
    public Policy findById(long id) {
        return repository.findById(id).map(this::toModel).orElse(null);
    }

    private PolicyEntity toEntity(Policy policy) {
        PolicyEntity e = new PolicyEntity();
        e.setPolicyNumber(policy.getPolicyNumber());
        e.setActive(policy.isActive());
        e.setExpiryDate(policy.getExpiryDate());
        if (policy.getCompany() != null) {
            CompanyEntity ce = new CompanyEntity();
            ce.setId(policy.getCompany().getId());
            ce.setName(policy.getCompany().getName());
            e.setCompany(ce);
        }
        return e;
    }

    private Policy toModel(PolicyEntity e) {
        if (e == null) return null;
        Policy policy = new Policy();
        policy.setId(e.getId());
        policy.setPolicyNumber(e.getPolicyNumber());
        policy.setActive(e.isActive());
        policy.setExpiryDate(e.getExpiryDate());
        if (e.getCompany() != null) {
            Company company = new Company();
            company.setId(e.getCompany().getId());
            company.setName(e.getCompany().getName());
            policy.setCompany(company);
        }
        return policy;
    }
}
