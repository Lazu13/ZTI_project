package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.config.SessionBean;
import pl.edu.agh.zti.dao.UserLeagueDao;
import pl.edu.agh.zti.enums.UserLeagueStatus;
import pl.edu.agh.zti.exceptions.IllegalLeagueIdException;
import pl.edu.agh.zti.exceptions.IllegalUserGroupStatus;
import pl.edu.agh.zti.model.EmptyJsonResponse;
import pl.edu.agh.zti.model.User;
import pl.edu.agh.zti.model.UserLeague;

import java.util.Optional;

/**
 * Spring service responsible for user_league-specific data loading
 */
@Service
public class UserLeagueService {

    private final UserLeagueDao userLeagueDao;
    private final SessionBean sessionBean;

    /**
     * Constructor with autowired arguments
     *
     * @param userLeagueDao data access object of user league
     * @param sessionBean   current session bean
     */
    @Autowired
    public UserLeagueService(
            UserLeagueDao userLeagueDao,
            SessionBean sessionBean) {
        this.userLeagueDao = userLeagueDao;
        this.sessionBean = sessionBean;
    }

    /**
     * Method allowing to invite user to league
     *
     * @param leagueId  id of league
     * @param userId    id of user
     * @param isNewUser if user is new user
     * @return empty json entity
     */
    @Loggable
    public ResponseEntity invite(Long leagueId, Long userId, boolean isNewUser) {
        UserLeague.Id userleagueId = new UserLeague.Id(userId, leagueId);
        Optional<UserLeague> userLeagueOpt = userLeagueDao.findById(userleagueId);

        if (userLeagueOpt.isPresent()) {
            UserLeague userLeague = userLeagueOpt.get();
            if (canBeInvited(userLeague)) {
                userLeague.setStatus(UserLeagueStatus.INVITED);
                userLeagueDao.save(userLeague);
            } else {
                throw new IllegalUserGroupStatus();
            }
        } else {

            UserLeague userLeague = UserLeague.builder()
                    .id(userleagueId)
                    .build();
            if (isNewUser) userLeague.setStatus(UserLeagueStatus.PARTICIPANT);
            else userLeague.setStatus(UserLeagueStatus.INVITED);
            userLeagueDao.save(userLeague);
        }
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    private boolean canBeInvited(UserLeague ug) {
        return ug.getStatus() == UserLeagueStatus.LEAVED || ug.getStatus() == UserLeagueStatus.REJECTED;
    }

    /**
     * Method allowing to accept invitation
     *
     * @param leagueId of league
     * @return empty json entity
     */
    @Loggable
    public ResponseEntity acceptInvitation(Long leagueId) {
        changeStatus(leagueId, UserLeagueStatus.INVITED, UserLeagueStatus.PARTICIPANT);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);

    }

    /**
     * Method allowing to reject invitation to league
     *
     * @param leagueId id of league
     * @return empty json entity
     */
    @Loggable
    public ResponseEntity rejectInvitation(Long leagueId) {
        changeStatus(leagueId, UserLeagueStatus.INVITED, UserLeagueStatus.REJECTED);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    /**
     * Method allowing to leave league
     *
     * @param leagueId id of league
     * @return empty json entity
     */
    @Loggable
    public ResponseEntity leave(Long leagueId) {
        changeStatus(leagueId, UserLeagueStatus.PARTICIPANT, UserLeagueStatus.LEAVED);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    private ResponseEntity changeStatus(Long leagueId, UserLeagueStatus requiredInitialStatus, UserLeagueStatus newStatus) {
        if (leagueId != 1L) {
            User currentUser = sessionBean.getCurrentUser();
            UserLeague.Id userleagueId = new UserLeague.Id(currentUser.getId(), leagueId);
            UserLeague userLeague = userLeagueDao.findById(userleagueId)
                    .filter(ug -> ug.getStatus() == requiredInitialStatus)
                    .orElseThrow(IllegalUserGroupStatus::new);
            userLeague.setStatus(newStatus);
            userLeagueDao.save(userLeague);
        } else {
            throw new IllegalLeagueIdException();
        }
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
