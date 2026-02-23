package app.domain.ports;

import app.domain.models.Patient;

public interface PatientPort {

        public boolean existsByDocument(String cedula);
        public void save(app.domain.models.Patient patient);
        public Patient findByDocument(Patient patient);
} 
