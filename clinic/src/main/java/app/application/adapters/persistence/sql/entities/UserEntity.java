package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "document", unique = true, nullable = false)
    private String document;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role")
    private String role;
    
}
