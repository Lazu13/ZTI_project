package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.schedulers.NextMatch;

/**
 * Spring repository (data access object) for [[NextMatch]] model
 */
@Repository
public interface NextMatchDao extends JpaRepository<NextMatch, Long> {
}
