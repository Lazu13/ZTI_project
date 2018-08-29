package pl.edu.agh.zti.model.predictions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.zti.model.User;
import pl.edu.agh.zti.model.match.Match;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Database entity representing user points
 */
@Entity
@Data
@Builder
@Table(name = "points_user")
@AllArgsConstructor
@NoArgsConstructor
public class PointsUser {
    @EmbeddedId
    private Id id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "match_id", insertable = false, updatable = false, nullable = false)
    private Match match;

    @Column(nullable = false)
    private Long points;

    @Column(nullable = false)
    private Long totalPoints;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id implements Serializable {
        @Column(name = "user_id", nullable = false)
        private Long userId;

        @Column(name = "match_id", nullable = false)
        private Long matchId;

    }

}
