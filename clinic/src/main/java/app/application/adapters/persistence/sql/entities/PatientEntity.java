package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "document", unique = true, nullable = false)
    private String document;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    private String gender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emergency_contact_id")
    private EmergencyContactEntity emergencyContact;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_id")
    private PolicyEntity policy;
}
