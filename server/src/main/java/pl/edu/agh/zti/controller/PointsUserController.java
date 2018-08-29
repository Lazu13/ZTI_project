package pl.edu.agh.zti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.zti.dto.PointsUserDto;
import pl.edu.agh.zti.service.PointsUserService;

/**
 * Rest controller with request mapping of "/points"
 */
@RestController
@RequestMapping("/points")
public class PointsUserController {

    private final PointsUserService pointsUserService;

    /**
     * Constructor with autowired argument
     *
     * @param pointsUserService service processing user points specific actions
     */
    @Autowired
    public PointsUserController(PointsUserService pointsUserService) {
        this.pointsUserService = pointsUserService;
    }

    /**
     * Method gathering user points in all of Gameweeks
     *
     * @param page number of page
     * @param size size number
     * @return page of [[PointsUserDto]]
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PointsUserDto> getByCurrentUser(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return pointsUserService.getByCurrentUser(page, size);
    }


    /**
     * Method gathering user points for specified user points in all of Gameweeks
     *
     * @param page number of page
     * @param size size number
     * @param userId id of user
     * @return page of [[PointsUserDto]]
     */
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PointsUserDto> getByUser(
            @RequestParam int page,
            @RequestParam int size,
            @PathVariable Long userId
    ) {
        return pointsUserService.getByUser(userId, page, size);
    }

    /**
     * Method gathering user points for all off the users in specified league
     *
     * @param page number of page
     * @param size size number
     * @param leagueId if of league
     * @return page of [[PointsUserDto]]
     */
    @GetMapping("/current/{leagueId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PointsUserDto> getByLeagueAndCurrentMatch(
            @RequestParam int page,
            @RequestParam int size,
            @PathVariable Long leagueId
    ) {
        return pointsUserService.getByLeagueAndCurrentMatch(leagueId, page, size);
    }

    /**
     * Method gathering user points for the users for specified
     * league and match
     *
     * @param page number of page
     * @param size size number
     * @param leagueId id of league
     * @param matchId id of match
     * @return page of [[PointsUserDto]]
     */
    @GetMapping("/{matchId}/{leagueId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PointsUserDto> getByLeagueAndMatch(
            @RequestParam int page,
            @RequestParam int size,
            @PathVariable Long leagueId,
            @PathVariable Long matchId
    ) {
        return pointsUserService.getByLeagueAndMatch(leagueId, matchId, page, size);
    }

}
