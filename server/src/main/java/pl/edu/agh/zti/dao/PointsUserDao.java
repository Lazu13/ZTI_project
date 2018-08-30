package pl.edu.agh.zti.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.predictions.PointsUser;

import java.util.List;
import java.util.Optional;

/**
 * Spring repository (data access object) for [[PointsUser]] model
 */
@Repository
public interface PointsUserDao extends JpaRepository<PointsUser, PointsUser.Id> {

    Page<PointsUser> findAllByIdUserId(Long userId, Pageable pageable);

    List<PointsUser> findAllByIdUserId(Long userId);

    Page<PointsUser> findAllByIdMatchId(Long matchId, Pageable pageable);

    Optional<PointsUser> findByIdUserIdAndIdMatchId(Long userId, Long matchId);

    Page<PointsUser> findAllByIdMatchIdAndIdUserIdIn(Long matchId, List<Long> userIds, Pageable pageable);
}

