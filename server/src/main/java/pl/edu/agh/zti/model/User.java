package pl.edu.agh.zti.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Database entity representing user
 */
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_time", nullable = false)
    private Date createTime = new Timestamp(System.currentTimeMillis());

    @OneToOne
    private Role role;

    @Column(nullable = false)
    private boolean active = true;

}
