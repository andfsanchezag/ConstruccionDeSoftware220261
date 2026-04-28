package app.application.adapters.persistence.sql;

import app.domain.models.clinic.ClinicalVisit;
import app.domain.models.identity.User;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.ports.out.ClinicalVisitPort;
import app.application.adapters.persistence.sql.entities.ClinicalVisitEntity;
import app.application.adapters.persistence.sql.entities.OrderEntity;
import app.application.adapters.persistence.sql.entities.PatientEntity;
import app.application.adapters.persistence.sql.entities.UserEntity;
import app.application.adapters.persistence.sql.repositories.ClinicalVisitRepository;
import org.springframework.stereotype.Service;

@Service
public class ClinicalVisitPersistenceAdapter implements ClinicalVisitPort {

    private final ClinicalVisitRepository repository;

    public ClinicalVisitPersistenceAdapter(ClinicalVisitRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ClinicalVisit clinicalVisit) {
        repository.save(toEntity(clinicalVisit));
    }

    private ClinicalVisitEntity toEntity(ClinicalVisit cv) {
        ClinicalVisitEntity e = new ClinicalVisitEntity();
        e.setDate(cv.getDate());
        e.setBloodPressure(cv.getBloodPressure());
        e.setTemperature(cv.getTemperature());
        e.setPulse(cv.getPulse());
        e.setOxygenLevel(cv.getOxygenLevel());
        e.setObservations(cv.getObservations());
        if (cv.getPatient() != null) {
            PatientEntity patientEntity = new PatientEntity();
            patientEntity.setId(cv.getPatient().getId());
            e.setPatient(patientEntity);
        }
        if (cv.getNurse() != null) {
            UserEntity nurseEntity = new UserEntity();
            nurseEntity.setId(cv.getNurse().getId());
            e.setNurse(nurseEntity);
        }
        if (cv.getOrder() != null) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(cv.getOrder().getId());
            e.setOrder(orderEntity);
        }
        return e;
    }

    public ClinicalVisit toModel(ClinicalVisitEntity e) {
        if (e == null) return null;
        ClinicalVisit cv = new ClinicalVisit();
        cv.setId(e.getId());
        cv.setDate(e.getDate());
        cv.setBloodPressure(e.getBloodPressure());
        cv.setTemperature(e.getTemperature());
        cv.setPulse(e.getPulse());
        cv.setOxygenLevel(e.getOxygenLevel());
        cv.setObservations(e.getObservations());
        if (e.getPatient() != null) {
            Patient p = new Patient();
            p.setId(e.getPatient().getId());
            p.setName(e.getPatient().getName());
            p.setDocument(e.getPatient().getDocument());
            cv.setPatient(p);
        }
        if (e.getNurse() != null) {
            User nurse = new User();
            nurse.setId(e.getNurse().getId());
            nurse.setName(e.getNurse().getName());
            nurse.setDocument(e.getNurse().getDocument());
            cv.setNurse(nurse);
        }
        if (e.getOrder() != null) {
            Order order = new Order();
            order.setId(e.getOrder().getId());
            cv.setOrder(order);
        }
        return cv;
    }
}
