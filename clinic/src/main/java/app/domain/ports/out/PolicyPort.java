package app.domain.ports.out;

import app.domain.models.patient.Policy;

public interface PolicyPort {

    void save(Policy policy);
    void update(Policy policy);
    Policy findById(long id);

}
