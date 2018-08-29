package pl.edu.agh.zti.model;

import lombok.Data;
import pl.edu.agh.zti.model.match.GroupMatch;

import javax.persistence.*;
import java.util.Set;

/**
 * Database entity representing group
 */
@Entity
@Data
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<Team> teams;

    @OneToMany(mappedBy = "matchGroup")
    private Set<GroupMatch> matches;

}
