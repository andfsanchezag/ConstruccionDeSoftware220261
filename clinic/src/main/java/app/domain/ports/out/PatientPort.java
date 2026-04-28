package app.domain.ports.out;

import app.domain.models.patient.Patient;
import java.util.List;

public interface PatientPort {

    boolean existsByDocument(String document);
    void save(Patient patient);
    void update(Patient patient);
    void deleteByDocument(String document);
    Patient findByDocument(String document);
    List<Patient> findAll();

}

