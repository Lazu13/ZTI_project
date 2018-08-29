package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.config.SessionBean;
import pl.edu.agh.zti.dao.MatchDao;
import pl.edu.agh.zti.dao.NextMatchDao;
import pl.edu.agh.zti.dao.PointsUserDao;
import pl.edu.agh.zti.dao.UserLeagueDao;
import pl.edu.agh.zti.dto.PointsUserDto;
import pl.edu.agh.zti.mapper.PointsUserLeagueMapper;
import pl.edu.agh.zti.model.User;
import pl.edu.agh.zti.model.UserLeague;
import pl.edu.agh.zti.model.match.Match;
import pl.edu.agh.zti.model.predictions.PointsUser;
import pl.edu.agh.zti.model.schedulers.NextMatch;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Spring service responsible for user_points-specific data loading
 */
@Service
@Transactional
public class PointsUserService {

    private final PointsUserDao pointsUserLeagueDao;
    private final PointsUserLeagueMapper pointsUserLeagueMapper;
    private final UserLeagueDao userLeagueDao;
    private final NextMatchDao nextMatchDao;
    private final MatchDao matchDao;

    private final SessionBean sessionBean;

    /**
     * Constructor with autowired arguments
     *
     * @param pointsUserLeagueDao    data access object of
     * @param pointsUserLeagueMapper user points mapper
     * @param sessionBean            current session bean
     * @param userLeagueDao          data access object of user league
     * @param nextMatchDao           data access object of next match
     * @param matchDao               data access object of match
     */
    @Autowired
    public PointsUserService(PointsUserDao pointsUserLeagueDao,
                             PointsUserLeagueMapper pointsUserLeagueMapper,
                             SessionBean sessionBean,
                             UserLeagueDao userLeagueDao,
                             NextMatchDao nextMatchDao,
                             MatchDao matchDao) {
        this.pointsUserLeagueDao = pointsUserLeagueDao;
        this.pointsUserLeagueMapper = pointsUserLeagueMapper;
        this.sessionBean = sessionBean;
        this.userLeagueDao = userLeagueDao;
        this.nextMatchDao = nextMatchDao;
        this.matchDao = matchDao;
    }

    /**
     * Method allowing to get current user's points
     *
     * @param page number of page
     * @param size size number
     * @return page of found user points
     */
    @Loggable
    public Page<PointsUserDto> getByCurrentUser(int page, int size) {
        User user = sessionBean.getCurrentUser();
        Long userId = user.getId();

        return getByUser(userId, page, size);
    }

    /**
     * Method allowing to get specified user's points
     *
     * @param userId id of user
     * @param page   number of page
     * @param size   size number
     * @return page of found user points
     */
    @Loggable
    public Page<PointsUserDto> getByUser(Long userId, int page, int size) {
        Page<PointsUser> pointsUserLeagues = pointsUserLeagueDao.findAllByIdUserId(userId, new PageRequest(page, size));
        return pointsUserLeagues.map(pointsUserLeague -> pointsUserLeagueMapper.entityToDto(pointsUserLeague));
    }

    private Comparator<Match> matchComparator = new Comparator<Match>() {
        @Override
        public int compare(Match match, Match t1) {
            return match.getDate().compareTo(t1.getDate());
        }
    };

    /**
     * Method allowing to get users in specified league points
     *
     * @param leagueId id of league
     * @param page     number of page
     * @param size     size number
     * @return page of found users in specified league points
     */
    @Loggable
    public Page<PointsUserDto> getByLeagueAndCurrentMatch(Long leagueId, int page, int size) {
        Long nextMatchId = 1L;
        NextMatch nextMatch = nextMatchDao.findOne(nextMatchId);
        List<Match> matchesBefore = matchDao.findAllByDateBefore(nextMatch.getMatch().getDate());

        Match currentMatch;
        if (matchesBefore.size() < 2) currentMatch = nextMatch.getMatch();
        else {
            matchesBefore.sort(matchComparator);

            currentMatch = matchesBefore.get(matchesBefore.size() - 1);
        }
        Long matchId = currentMatch.getId();

        return getByLeagueAndMatch(leagueId, matchId, page, size);
    }

    /**
     * Method allowing to get points for every user in league
     * and in specified match
     *
     * @param leagueId id of league
     * @param matchId  id of match
     * @param page     number of page
     * @param size     size number
     * @return page of found users points
     */
    @Loggable
    public Page<PointsUserDto> getByLeagueAndMatch(Long leagueId, Long matchId, int page, int size) {
        List<UserLeague> usersInLeague = userLeagueDao.findAllByIdLeagueId(leagueId);
        List<Long> userIds = new ArrayList<>();
        for (UserLeague userLeague : usersInLeague) {
            userIds.add(userLeague.getUser().getId());
        }

        Page<PointsUser> pointsUserLeagues = pointsUserLeagueDao.findAllByIdMatchIdAndIdUserIdIn(matchId, userIds,
                new PageRequest(page, size));
        return pointsUserLeagues.map(pointsUserLeague -> pointsUserLeagueMapper.entityToDto(pointsUserLeague));
    }

}
