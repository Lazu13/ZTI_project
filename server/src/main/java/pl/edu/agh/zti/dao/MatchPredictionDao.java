package pl.edu.agh.zti.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.predictions.MatchPrediction;

import java.util.Optional;

/**
 * Spring repository (data access object) for [[MatchPrediction]] model
 */
@Repository
public interface MatchPredictionDao extends PagingAndSortingRepository<MatchPrediction, MatchPrediction.Id> {

    Optional<MatchPrediction> findById(MatchPrediction.Id id);

    Page<MatchPrediction> findAllByIdUserId(Long id, Pageable pageable);

}
