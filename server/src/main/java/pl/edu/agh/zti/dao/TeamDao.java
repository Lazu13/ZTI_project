package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.Team;

/**
 * Spring repository (data access object) for [[Team]] model
 */
@Repository
public interface TeamDao extends JpaRepository<Team, Long> {
}
