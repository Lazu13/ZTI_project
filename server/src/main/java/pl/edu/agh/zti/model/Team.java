package pl.edu.agh.zti.model;

import lombok.Data;
import pl.edu.agh.zti.model.match.GroupMatch;
import pl.edu.agh.zti.model.match.KnockoutMatch;

import javax.persistence.*;
import java.util.Set;

/**
 * Database entity representing team
 */
@Entity
@Data
@Table(name = "teams")
public class Team {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "teamH")
    private Set<KnockoutMatch> matchesH;

    @OneToMany(mappedBy = "teamA")
    private Set<KnockoutMatch> matchesA;

    @OneToMany(mappedBy = "teamH")
    private Set<GroupMatch> matchesGH;

    @OneToMany(mappedBy = "teamA")
    private Set<GroupMatch> matchesGA;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable = true, updatable = false, nullable = false)
    private Group group;
}
