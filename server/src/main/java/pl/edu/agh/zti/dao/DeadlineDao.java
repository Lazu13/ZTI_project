package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.schedulers.Deadline;

import java.util.Date;
import java.util.List;

/**
 * Spring repository (data access object) for [[Deadline]] model
 */
@Repository
public interface DeadlineDao extends JpaRepository<Deadline, Long> {

    List<Deadline> findAllByDateAfter(Date date);

    List<Deadline> findAllByDateBefore(Date date);

}
