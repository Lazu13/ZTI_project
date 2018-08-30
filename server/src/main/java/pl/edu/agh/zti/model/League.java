package pl.edu.agh.zti.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Database entity representing league
 */
@Entity
@Data
@Table(name = "leagues")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "create_time", nullable = false)
    private Date createTime = new Timestamp(System.currentTimeMillis());

    @OneToMany(mappedBy = "league")
    private List<UserLeague> participants;

    private boolean active = true;
}
