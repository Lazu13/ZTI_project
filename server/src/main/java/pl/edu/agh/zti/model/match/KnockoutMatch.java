package pl.edu.agh.zti.model.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.zti.model.Team;

import javax.persistence.*;

/**
 * Database entity representing knockout match
 */
@Entity
@Data
@Builder
@Table(name = "knockout_matches")
@NoArgsConstructor
@AllArgsConstructor
public class KnockoutMatch {
    @Id
    @Column(name = "match_id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Match match;

    @OneToOne
    @JoinColumn(name = "match_id")
    private StatsMatch statsMatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_team_h", updatable = false)
    private Team teamH;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_team_a", updatable = false)
    private Team teamA;

    @Column(nullable = false)
    private String teamHDescription;

    @Column(nullable = false)
    private String teamADescription;

    @Column(nullable = false)
    private String stage;

}
