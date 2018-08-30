package pl.edu.agh.zti.model.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.zti.model.Group;
import pl.edu.agh.zti.model.Team;

import javax.persistence.*;

/**
 * Database entity group match
 */
@Entity
@Data
@Builder
@Table(name = "group_matches")
@NoArgsConstructor
@AllArgsConstructor
public class GroupMatch {
    @Id
    @Column(name = "match_id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Match match;

    @OneToOne
    @JoinColumn(name = "match_id")
    private StatsMatch statsMatch;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_match_group", updatable = false, nullable = false)
    private Group matchGroup;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_team_h", updatable = false, nullable = false)
    private Team teamH;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_team_a", updatable = false, nullable = false)
    private Team teamA;
    
}
