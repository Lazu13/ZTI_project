package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.match.GroupMatch;

/**
 * Spring repository (data access object) for [[GroupMatch]] model
 */
@Repository
public interface GroupMatchDao extends JpaRepository<GroupMatch, Long> {
}
