package app.domain.models.billing;

import app.domain.models.identity.User;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Invoice {

    private long id;

    private Patient patient;

    private User doctor;

    private Date issueDate;

    private double totalAmount;

    private double copayment;

    private double insuranceCoverage;

    private double patientPayment;

    private boolean policyApplied;

    private Policy policy;

    private List<InvoiceItem> items;
}
