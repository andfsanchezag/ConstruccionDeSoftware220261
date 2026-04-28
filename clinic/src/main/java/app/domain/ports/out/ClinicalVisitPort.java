package app.domain.ports.out;

import app.domain.models.clinic.ClinicalVisit;

public interface ClinicalVisitPort {

    void save(ClinicalVisit clinicalVisit);

}
