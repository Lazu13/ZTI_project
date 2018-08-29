package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.match.StatsMatch;

/**
 * Spring repository (data access object) for [[StatsMatch]] model
 */
@Repository
public interface StatsMatchDao extends JpaRepository<StatsMatch, Long> {
}
