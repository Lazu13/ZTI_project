package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.dao.NextMatchDao;
import pl.edu.agh.zti.dto.NextMatchDto;
import pl.edu.agh.zti.mapper.NextMatchMapper;
import pl.edu.agh.zti.model.schedulers.NextMatch;

import javax.transaction.Transactional;

/**
 * Spring service responsible for next_match-specific data loading
 */
@Service
@Transactional
public class NextMatchService {

    private final NextMatchDao nextMatchDao;
    private final NextMatchMapper nextMatchMapper;

    /**
     * Constructor with autowired arguments
     *
     * @param nextMatchDao data access object of next match
     * @param nextMatchMapper next match mapper
     */
    @Autowired
    public NextMatchService(
            NextMatchDao nextMatchDao,
            NextMatchMapper nextMatchMapper
    ) {
        this.nextMatchDao = nextMatchDao;
        this.nextMatchMapper = nextMatchMapper;
    }

    /**
     * Method allowing to get next match
     *
     * @return next match
     */
    @Loggable
    public NextMatchDto get() {
        Long nextMatchId = 1L;
        NextMatch nextMatch = nextMatchDao.findOne(nextMatchId);
        return nextMatchMapper.entityToDto(nextMatch);
    }

}
