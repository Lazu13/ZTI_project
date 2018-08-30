package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.deadline.DeadlineAfterCheckerable;
import pl.edu.agh.zti.annotations.deadline.DeadlineBeforeCheckerable;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.config.SessionBean;
import pl.edu.agh.zti.dao.MatchPredictionDao;
import pl.edu.agh.zti.dto.predictions.GetMatchPredictionDto;
import pl.edu.agh.zti.dto.predictions.PostMatchPredictionDto;
import pl.edu.agh.zti.exceptions.IllegalPredictionDataException;
import pl.edu.agh.zti.mapper.MatchPredictionMapper;
import pl.edu.agh.zti.model.User;
import pl.edu.agh.zti.model.predictions.MatchPrediction;
import pl.edu.agh.zti.model.predictions.MatchPredictions;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring service responsible for predictions-specific data loading
 */
@Service
@Transactional
public class PredictionService {

    private final MatchPredictionDao matchPredictionDao;
    private final MatchPredictionMapper matchPredictionMapper;
    private final SessionBean sessionBean;

    /**
     * Constructor with autowired arguments
     *
     * @param matchPredictionDao    data access object of match prediction
     * @param matchPredictionMapper match prediction mapper
     * @param sessionBean           current session bean
     */
    @Autowired
    public PredictionService(MatchPredictionDao matchPredictionDao,
                             MatchPredictionMapper matchPredictionMapper,
                             SessionBean sessionBean) {
        this.matchPredictionDao = matchPredictionDao;
        this.matchPredictionMapper = matchPredictionMapper;
        this.sessionBean = sessionBean;
    }

    /**
     * Method allowing to get current user's predictions
     *
     * @param page number of page
     * @param size size number
     * @return page of found predictions
     */
    @Loggable
    public Page<GetMatchPredictionDto> getByCurrentUser(int page, int size) {
        User user = sessionBean.getCurrentUser();
        return getByUser(user.getId(), page, size);
    }

    /**
     * Method allowing to get specified user's predictions
     *
     * @param id   of user
     * @param page number of page
     * @param size size number
     * @return page of found predictions
     */
    @Loggable
    @DeadlineBeforeCheckerable
    public Page<GetMatchPredictionDto> getByUser(long id, int page, int size) {
        Page<MatchPrediction> predictions = matchPredictionDao.findAllByIdUserId(id, new PageRequest(page, size));
        return predictions.map(prediction -> matchPredictionMapper.getEntityToDto(prediction));
    }

    /**
     * Method allowing to make prediction
     *
     * @param predictionDto prediction data
     */
    @Loggable
    @DeadlineAfterCheckerable
    public void makePrediction(PostMatchPredictionDto predictionDto) {
        User user = sessionBean.getCurrentUser();
        MatchPrediction.Id matchPredictionId = new MatchPrediction.Id(user.getId(), predictionDto.getMatchId());
        MatchPrediction matchPrediction = matchPredictionMapper.dtoToEntity(predictionDto);
        matchPrediction.setId(matchPredictionId);
        matchPredictionDao.save(matchPrediction);
    }

    /**
     * Method allowing to make multiple predictions
     *
     * @param predictionDtos predictions data
     */
    @Loggable
    @DeadlineAfterCheckerable
    public void makePredictions(MatchPredictions predictionDtos) {
        User user = sessionBean.getCurrentUser();
        Long userId = user.getId();

        List<MatchPrediction> matchPredictions = new ArrayList<>();
        for (PostMatchPredictionDto predictionDto : predictionDtos) {
            if (predictionDto.isKnockoutStage() && (predictionDto.getTeamAId() == null || predictionDto.getTeamHId() == null))
                throw new IllegalPredictionDataException();

            MatchPrediction.Id matchPredictionId = new MatchPrediction.Id(userId, predictionDto.getMatchId());
            MatchPrediction matchPrediction = matchPredictionMapper.dtoToEntity(predictionDto);
            matchPrediction.setId(matchPredictionId);
            matchPredictions.add(matchPrediction);
        }
        matchPredictionDao.save(matchPredictions);
    }

}
