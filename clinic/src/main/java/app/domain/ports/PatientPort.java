package app.domain.ports;

public interface PatientPort {

        public boolean existsByDocument(String cedula);
        public void save(app.domain.models.Patient patient);
} 
