package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.patient.Policy;
import app.domain.ports.PolicyPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdatePolicy {

    private PolicyPort policyPort;

    @Autowired
    public UpdatePolicy(PolicyPort policyPort) {
        this.policyPort = policyPort;
    }

    public void updatePolicy(Policy policy) throws BusinessException {
        Policy existing = policyPort.findById(policy.getId());
        if (existing == null) {
            throw new BusinessException("No existe una poliza con ese id");
        }
        policyPort.update(policy);
    }
}
