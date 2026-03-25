package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.billing.Invoice;
import app.domain.models.patient.Patient;
import app.domain.ports.InvoicePort;
import app.domain.ports.PatientPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FindInvoice {

    private InvoicePort invoicePort;
    private PatientPort patientPort;

    @Autowired
    public FindInvoice(InvoicePort invoicePort, PatientPort patientPort) {
        this.invoicePort = invoicePort;
        this.patientPort = patientPort;
    }

    public Invoice findById(long id) throws BusinessException {
        Invoice invoice = invoicePort.findById(id);
        if (invoice == null) {
            throw new BusinessException("No existe una factura con ese id");
        }
        return invoice;
    }

    public List<Invoice> findByPatient(String patientDocument) throws BusinessException {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new BusinessException("No existe un paciente con esa cedula");
        }
        return invoicePort.findByPatient(patient);
    }
}
