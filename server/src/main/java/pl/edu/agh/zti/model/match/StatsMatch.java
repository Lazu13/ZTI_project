package pl.edu.agh.zti.model.match;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Database entity representing match statistics
 * It implements Comparable interface
 */
@Entity
@Data
@Builder
@Table(name = "stats_match")
@NoArgsConstructor
@AllArgsConstructor
public class StatsMatch implements Comparable<StatsMatch> {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private Match match;

    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "result_home", nullable = false)
    private Integer resultH;

    @Min(value = 0)
    @Max(value = 50)
    @Column(name = "result_away", nullable = false)
    private Integer resultA;

    /**
     * Overridden compareTo method
     *
     * @param o stats match
     * @return comparision result
     */
    @Override
    public int compareTo(StatsMatch o) {
        return this.match.getDate().compareTo(o.getMatch().getDate());
    }
}
