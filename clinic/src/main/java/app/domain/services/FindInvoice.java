package app.domain.services;

import app.domain.Exceptions.NotFoundException;
import app.domain.models.billing.Invoice;
import app.domain.models.patient.Patient;
import app.domain.ports.out.InvoicePort;
import app.domain.ports.out.PatientPort;

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

    public Invoice findById(long id) throws NotFoundException {
        Invoice invoice = invoicePort.findById(id);
        if (invoice == null) {
            throw new NotFoundException("No existe una factura con ese id");
        }
        return invoice;
    }

    public List<Invoice> findByPatient(String patientDocument) throws NotFoundException {
        Patient patient = patientPort.findByDocument(patientDocument);
        if (patient == null) {
            throw new NotFoundException("No existe un paciente con esa cedula");
        }
        return invoicePort.findByPatient(patient);
    }
}
