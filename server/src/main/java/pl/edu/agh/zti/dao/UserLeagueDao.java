package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.UserLeague;

import java.util.List;
import java.util.Optional;

/**
 * Spring repository (data access object) for [[UserLeague]] model
 */
@Repository
public interface UserLeagueDao extends JpaRepository<UserLeague, UserLeague.Id> {
    Optional<UserLeague> findById(UserLeague.Id id);

    List<UserLeague> findAllByIdLeagueId(Long leagueId);
}
