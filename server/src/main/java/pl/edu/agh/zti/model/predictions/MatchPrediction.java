package pl.edu.agh.zti.model.predictions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.zti.model.Team;
import pl.edu.agh.zti.model.User;
import pl.edu.agh.zti.model.match.Match;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Database entity representing match prediction
 */
@Entity
@Data
@Table(name = "match_prediction")
public class MatchPrediction {
    @EmbeddedId
    private Id id;

    @ManyToOne
    @JoinColumn(name = "match_id", insertable = false, updatable = false, nullable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
    private User user;

    @Min(value = 0)
    @Max(value = 50)
    @Column(nullable = false)
    private int resultH;

    @Min(value = 0)
    @Max(value = 50)
    @Column(nullable = false)
    private int resultA;

    @Column(nullable = false)
    private boolean knockoutStage;

    @ManyToOne
    @JoinColumn(name = "teamH_id")
    private Team teamH;

    @ManyToOne
    @JoinColumn(name = "teamA_id")
    private Team teamA;

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
