package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.dao.GroupMatchDao;
import pl.edu.agh.zti.dao.KnockoutMatchDao;
import pl.edu.agh.zti.dto.match.GroupMatchDto;
import pl.edu.agh.zti.dto.match.KnockoutMatchDto;
import pl.edu.agh.zti.mapper.GroupMatchMapper;
import pl.edu.agh.zti.mapper.KnockoutMatchMapper;
import pl.edu.agh.zti.model.match.GroupMatch;
import pl.edu.agh.zti.model.match.KnockoutMatch;

import javax.transaction.Transactional;

/**
 * Spring service responsible for match-specific data loading
 */
@Service
@Transactional
public class MatchService {

    private final KnockoutMatchDao knockoutMatchDao;
    private final KnockoutMatchMapper knockoutMatchMapper;

    private final GroupMatchDao groupMatchDao;
    private final GroupMatchMapper groupMatchMapper;

    /**
     * Constructor with autowired arguments
     *
     * @param matchDao data access object of match
     * @param matchMapper match mapper
     * @param groupMatchDao data access object of group match
     * @param groupMatchMapper group match mapper
     */
    @Autowired
    public MatchService(KnockoutMatchDao matchDao, KnockoutMatchMapper matchMapper, GroupMatchDao groupMatchDao, GroupMatchMapper groupMatchMapper) {
        this.knockoutMatchDao = matchDao;
        this.knockoutMatchMapper = matchMapper;
        this.groupMatchDao = groupMatchDao;
        this.groupMatchMapper = groupMatchMapper;
    }

    /**
     * Method allowing to get all knockout matches
     *
     * @param page number of page
     * @param size size number
     * @return page of knockout matches
     */
    @Loggable
    public Page<KnockoutMatchDto> getAllKnockoutMatches(int page, int size) {
        Page<KnockoutMatch> matches = knockoutMatchDao.findAll(new PageRequest(page, size));
        return matches.map(knockoutMatchMapper::entityToDto);
    }

    /**
     * Method allowing to get all group matches
     *
     * @param page number of page
     * @param size size number
     * @return page of group matches
     */
    @Loggable
    public Page<GroupMatchDto> getAllGroupMatches(int page, int size) {
        Page<GroupMatch> matches = groupMatchDao.findAll(new PageRequest(page, size));
        return matches.map(groupMatchMapper::entityToDto);
    }

}
