package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "clinical_visits")
public class ClinicalVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private UserEntity nurse;

    @Column(name = "date")
    private Date date;

    @Column(name = "blood_pressure")
    private String bloodPressure;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "pulse")
    private int pulse;

    @Column(name = "oxygen_level")
    private double oxygenLevel;

    @Column(name = "observations")
    private String observations;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
