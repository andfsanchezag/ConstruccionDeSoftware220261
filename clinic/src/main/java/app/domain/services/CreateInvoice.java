package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.billing.Invoice;
import app.domain.models.billing.InvoiceItem;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import app.domain.models.patient.PolicyHistory;
import app.domain.ports.InvoicePort;
import app.domain.ports.PatientPort;
import app.domain.ports.PolicyHistoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class CreateInvoice {

    private static final double COPAYMENT_AMOUNT = 50000;
    private static final double ANNUAL_COPAYMENT_LIMIT = 1000000;

    private InvoicePort invoicePort;
    private PatientPort patientPort;
    private PolicyHistoryPort policyHistoryPort;

    @Autowired
    public CreateInvoice(InvoicePort invoicePort, PatientPort patientPort, PolicyHistoryPort policyHistoryPort) {
        this.invoicePort = invoicePort;
        this.patientPort = patientPort;
        this.policyHistoryPort = policyHistoryPort;
    }

    public void createInvoice(Invoice invoice) throws BusinessException {
        Patient patient = patientPort.findByDocument(invoice.getPatient().getDocument());
        if (patient == null) {
            throw new BusinessException("No existe el paciente");
        }
        if (invoice.getItems() == null || invoice.getItems().isEmpty()) {
            throw new BusinessException("La factura debe tener al menos un item");
        }

        double totalAmount = calculateTotal(invoice);
        invoice.setTotalAmount(totalAmount);
        invoice.setIssueDate(new Date(System.currentTimeMillis()));
        invoice.setPatient(patient);

        applyBillingRules(invoice, patient, totalAmount);

        invoicePort.save(invoice);
    }

    private double calculateTotal(Invoice invoice) {
        double total = 0;
        for (InvoiceItem item : invoice.getItems()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    private void applyBillingRules(Invoice invoice, Patient patient, double totalAmount) {
        Policy policy = patient.getPolicy();
        boolean hasActivePolicy = policy != null && policy.isActive();
        invoice.setPolicyApplied(hasActivePolicy);

        if (hasActivePolicy) {
            int currentYear = LocalDate.now().getYear();
            PolicyHistory history = policyHistoryPort.findByPatientAndYear(patient, currentYear);
            double accumulated = history != null ? history.getAccumulatedCopayment() : 0;

            if (accumulated >= ANNUAL_COPAYMENT_LIMIT) {
                invoice.setCopayment(0);
                invoice.setInsuranceCoverage(totalAmount);
                invoice.setPatientPayment(0);
            } else {
                invoice.setCopayment(COPAYMENT_AMOUNT);
                invoice.setInsuranceCoverage(totalAmount - COPAYMENT_AMOUNT);
                invoice.setPatientPayment(COPAYMENT_AMOUNT);
                updatePolicyHistory(patient, history, currentYear);
            }
        } else {
            invoice.setCopayment(0);
            invoice.setInsuranceCoverage(0);
            invoice.setPatientPayment(totalAmount);
        }
    }

    private void updatePolicyHistory(Patient patient, PolicyHistory history, int year) {
        if (history == null) {
            PolicyHistory newHistory = new PolicyHistory();
            newHistory.setPatient(patient);
            newHistory.setYear(year);
            newHistory.setAccumulatedCopayment(COPAYMENT_AMOUNT);
            policyHistoryPort.save(newHistory);
        } else {
            history.setAccumulatedCopayment(history.getAccumulatedCopayment() + COPAYMENT_AMOUNT);
            policyHistoryPort.update(history);
        }
    }
}
