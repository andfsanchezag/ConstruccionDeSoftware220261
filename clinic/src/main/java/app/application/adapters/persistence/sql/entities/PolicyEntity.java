package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "policies")
public class PolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @Column(name = "policy_number", nullable = false)
    private String policyNumber;

    @Column(name = "active")
    private boolean active;

    @Column(name = "expiry_date")
    private Date expiryDate;
}
