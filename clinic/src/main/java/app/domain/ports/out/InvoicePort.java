package app.domain.ports.out;

import app.domain.models.billing.Invoice;
import app.domain.models.patient.Patient;
import java.util.List;

public interface InvoicePort {

    void save(Invoice invoice);
    Invoice findById(long id);
    List<Invoice> findByPatient(Patient patient);

}
