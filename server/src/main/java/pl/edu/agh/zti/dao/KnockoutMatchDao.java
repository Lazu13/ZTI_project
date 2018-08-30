package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.match.KnockoutMatch;

/**
 * Spring repository (data access object) for [[KnockoutMatch]] model
 */
@Repository
public interface KnockoutMatchDao extends JpaRepository<KnockoutMatch, Long> {
}
