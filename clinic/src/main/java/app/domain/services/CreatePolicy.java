package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import app.domain.ports.out.PatientPort;
import app.domain.ports.out.PolicyPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePolicy {

    private PolicyPort policyPort;
    private PatientPort patientPort;

    @Autowired
    public CreatePolicy(PolicyPort policyPort, PatientPort patientPort) {
        this.policyPort = policyPort;
        this.patientPort = patientPort;
    }

    public void createPolicy(String patientDocument, Policy policy) throws BusinessException {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new BusinessException("No existe un paciente con esa cedula");
        }
        policyPort.save(policy);
        patient.setPolicy(policy);
        patientPort.update(patient);
    }
}
