package pl.edu.agh.zti.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.League;
import pl.edu.agh.zti.model.User;

import java.util.Optional;

/**
 * Spring repository (data access object) for [[League]] model
 */
@Repository
public interface LeagueDao extends PagingAndSortingRepository<League, Long> {

    Optional<League> findById(Long id);

    @Query("SELECT g FROM League g WHERE g.active = true and g.id IN (SELECT ug.league.id FROM UserLeague ug WHERE ug.user=:user and ug.status in ('PARTICIPANT', 'ADMIN'))")
    Page<League> getAllByUser(@Param("user") User user, Pageable pageable);

    @Query("SELECT g FROM League g WHERE g.active = true and g.id IN (SELECT ug.league.id FROM UserLeague ug WHERE ug.user=:user and ug.status = 'INVITED')")
    Page<League> getAllInvitations(@Param("user") User user, Pageable pageable);

}
