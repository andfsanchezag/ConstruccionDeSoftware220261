package app.application.adapters.persistence.sql;

import app.domain.models.clinic.ClinicalVisit;
import app.domain.models.identity.User;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.ports.ClinicalVisitPort;
import app.application.adapters.persistence.sql.entities.ClinicalVisitEntity;
import app.application.adapters.persistence.sql.repositories.ClinicalVisitRepository;
import app.application.adapters.persistence.sql.repositories.OrderRepository;
import app.application.adapters.persistence.sql.repositories.PatientRepository;
import app.application.adapters.persistence.sql.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ClinicalVisitPersistenceAdapter implements ClinicalVisitPort {

    private final ClinicalVisitRepository repository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ClinicalVisitPersistenceAdapter(ClinicalVisitRepository repository, PatientRepository patientRepository,
                                           UserRepository userRepository, OrderRepository orderRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
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
            e.setPatient(patientRepository.findByDocument(cv.getPatient().getDocument()));
        }
        if (cv.getNurse() != null) {
            e.setNurse(userRepository.findByDocument(cv.getNurse().getDocument()));
        }
        if (cv.getOrder() != null) {
            orderRepository.findById(cv.getOrder().getId()).ifPresent(e::setOrder);
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
