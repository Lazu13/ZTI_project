package pl.edu.agh.zti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.zti.model.EmptyJsonResponse;
import pl.edu.agh.zti.service.UserLeagueService;

/**
 * Rest controller with request mapping of "/leagues/{leagueId}"
 */
@RestController
@RequestMapping("/leagues/{leagueId}")
public class UserLeagueController {

    private final UserLeagueService userLeagueService;

    /**
     * Constructor with autowired argument
     *
     * @param userLeagueService service processing user league specific actions
     */
    @Autowired
    public UserLeagueController(UserLeagueService userLeagueService) {
        this.userLeagueService = userLeagueService;
    }

    /**
     * Method invitin user to league
     *
     * @param leagueId id of league
     * @param userId id of user
     * @return empty json response
     */
    @PostMapping("/users/{userId}/invite")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity invite(@PathVariable Long leagueId, @PathVariable Long userId) {
        userLeagueService.invite(leagueId, userId, false);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    /**
     * Method accepting current user's invitation to league
     *
     * @param leagueId id of league
     * @return empty json response
     */
    @PostMapping("/invitation/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity acceptInvitation(@PathVariable Long leagueId) {
        userLeagueService.acceptInvitation(leagueId);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    /**
     * Method rejecting current user's invitation to league
     *
     * @param leagueId id of league
     * @return empty json response
     */
    @PostMapping("/invitation/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity rejectInvitation(@PathVariable Long leagueId) {
        userLeagueService.rejectInvitation(leagueId);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);

    }

    /**
     * Method leaving current user from league
     *
     * @param leagueId id of league
     * @return empty json response
     */
    @PostMapping("/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity leave(@PathVariable Long leagueId) {
        userLeagueService.leave(leagueId);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
