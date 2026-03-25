package app.domain.ports;

import app.domain.models.clinic.ClinicalVisit;

public interface ClinicalVisitPort {

    void save(ClinicalVisit clinicalVisit);

}
