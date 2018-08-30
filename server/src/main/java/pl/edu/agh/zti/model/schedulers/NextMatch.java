package pl.edu.agh.zti.model.schedulers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.zti.model.match.Match;

import javax.persistence.*;

/**
 * Database entity representing next match
 */
@Entity
@Data
@Builder
@Table(name = "next_match")
@NoArgsConstructor
@AllArgsConstructor
public class NextMatch {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;

}
