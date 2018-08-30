package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.match.Match;

import java.util.Date;
import java.util.List;

/**
 * Spring repository (data access object) for [[Match]] model
 */
@Repository
public interface MatchDao extends JpaRepository<Match, Long> {
    List<Match> findAllByDateBefore(Date date);

    List<Match> findAllByDateBeforeOrderByDate(Date date);

    List<Match> findByFinishedFalseOrderByDate();

    List<Match> findTop10ByOrderByDateDesc();
}
