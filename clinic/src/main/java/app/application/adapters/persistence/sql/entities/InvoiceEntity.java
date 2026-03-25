package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private UserEntity doctor;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "copayment")
    private double copayment;

    @Column(name = "insurance_coverage")
    private double insuranceCoverage;

    @Column(name = "patient_payment")
    private double patientPayment;

    @Column(name = "policy_applied")
    private boolean policyApplied;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private PolicyEntity policy;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItemEntity> items;
}
