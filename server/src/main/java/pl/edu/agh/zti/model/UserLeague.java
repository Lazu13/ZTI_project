package pl.edu.agh.zti.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.zti.enums.UserLeagueStatus;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Database entity representing user leagues
 */
@Entity
@Data
@Builder
@Table(name = "user_leagues")
@NoArgsConstructor
@AllArgsConstructor
public class UserLeague {
    @EmbeddedId
    private Id id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "league_id", insertable = false, updatable = false, nullable = false)
    private League league;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserLeagueStatus status;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id implements Serializable {
        @Column(name = "user_id", nullable = false)
        private Long userId;

        @Column(name = "league_id", nullable = false)
        private Long leagueId;
    }

}
