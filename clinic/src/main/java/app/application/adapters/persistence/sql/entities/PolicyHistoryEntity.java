package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "policy_history")
public class PolicyHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "accumulated_copayment")
    private double accumulatedCopayment;
}
