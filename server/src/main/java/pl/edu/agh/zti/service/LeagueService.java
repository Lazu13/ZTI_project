package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.config.SessionBean;
import pl.edu.agh.zti.dao.LeagueDao;
import pl.edu.agh.zti.dao.UserLeagueDao;
import pl.edu.agh.zti.dto.leagues.LeagueInsertDto;
import pl.edu.agh.zti.dto.leagues.LeagueReturnDto;
import pl.edu.agh.zti.enums.UserLeagueStatus;
import pl.edu.agh.zti.exceptions.NotFoundException;
import pl.edu.agh.zti.mapper.LeagueMapper;
import pl.edu.agh.zti.model.League;
import pl.edu.agh.zti.model.User;
import pl.edu.agh.zti.model.UserLeague;

import javax.transaction.Transactional;

/**
 * Spring service responsible for league-specific data loading
 */
@Service
@Transactional
public class LeagueService {

    private final LeagueDao leagueDao;
    private final LeagueMapper leagueMapper;
    private final UserLeagueDao userLeagueDao;
    private final SessionBean sessionBean;

    /**
     * Constructor with autowired arguments
     *
     * @param leagueDao data access object of league
     * @param leagueMapper league mapper
     * @param userLeagueDao data access object of user league
     * @param sessionBean current session bean
     */
    @Autowired
    public LeagueService(
            LeagueDao leagueDao,
            LeagueMapper leagueMapper,
            UserLeagueDao userLeagueDao,
            SessionBean sessionBean) {
        this.leagueDao = leagueDao;
        this.leagueMapper = leagueMapper;
        this.sessionBean = sessionBean;
        this.userLeagueDao = userLeagueDao;
    }

    /**
     * Method allowing to save new league
     *
     * @param dto data of league to save
     */
    @Loggable
    public void save(LeagueInsertDto dto) {
        League league = leagueMapper.dtoToEntity(dto);
        league = leagueDao.save(league);
        addAdminToGroup(league);
    }

    private void addAdminToGroup(League league) {
        User admin = sessionBean.getCurrentUser();
        UserLeague.Id userLeagueId = new UserLeague.Id(admin.getId(), league.getId());
        UserLeague userLeague = UserLeague.builder()
                .id(userLeagueId)
                .status(UserLeagueStatus.ADMIN)
                .build();
        userLeagueDao.save(userLeague);
    }

    /**
     * Method allowing to delete user
     *
     * @param id id of user to delete
     */
    @Loggable
    public void delete(Long id) {
        League league = leagueDao.findById(id).orElseThrow(NotFoundException::new);
        league.setActive(false);
        leagueDao.save(league);
    }

    /**
     * Method allowing to get all leagues
     *
     * @param page number of page
     * @param size size number
     * @return page of leagues
     */
    @Loggable
    public Page<LeagueReturnDto> getAllLeagues(int page, int size) {
        Page<League> leagues = leagueDao.findAll(new PageRequest(page, size));
        return leagues.map(league -> leagueMapper.entityToDto(league));
    }

    /**
     * Method allowing to get all user leagues
     *
     * @param page number of page
     * @param size size number
     * @return page of leagues
     */
    @Loggable
    public Page<LeagueReturnDto> getLeaguesByUser(int page, int size) {
        User user = sessionBean.getCurrentUser();
        Page<League> leagues = leagueDao.getAllByUser(user, new PageRequest(page, size));
        return leagues.map(league -> leagueMapper.entityToDto(league));
    }

    /**
     * Method allowing to get current user leagues invitations
     *
     * @param page number of page
     * @param size size number
     * @return page of leagues
     */
    @Loggable
    public Page<LeagueReturnDto> getLeagueInvitations(int page, int size) {
        User user = sessionBean.getCurrentUser();
        Page<League> leagues = leagueDao.getAllInvitations(user, new PageRequest(page, size));
        return leagues.map(league -> leagueMapper.entityToDto(league));

    }

}
