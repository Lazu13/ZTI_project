package pl.edu.agh.zti.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Database entity representing user role
 */
@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;
}
