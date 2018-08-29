package pl.edu.agh.zti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.zti.dto.leagues.LeagueInsertDto;
import pl.edu.agh.zti.dto.leagues.LeagueReturnDto;
import pl.edu.agh.zti.model.EmptyJsonResponse;
import pl.edu.agh.zti.service.LeagueService;

import javax.validation.Valid;

/**
 * Rest controller with request mapping of "/leagues"
 */
@RestController
@RequestMapping("/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    /**
     * Constructor with autowired argument
     *
     * @param leagueService service processing league specific actions
     */
    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    /**
     * Method saving [[LeagueInsert]]
     *
     * @param dto data transfer object of [[LeagueInsert]]
     * @return empty json
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity save(@Valid @RequestBody LeagueInsertDto dto) {
        leagueService.save(dto);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    /**
     * Method gathering all leagues of [[LeagueReturnDto]] in pages
     * It requires ADMIN role
     *
     * @param page number of page
     * @param size size number
     * @return page of [[LeagueReturnDto]]
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<LeagueReturnDto> getAll(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return leagueService.getAllLeagues(page, size);
    }

    /**
     * Method gathering users invitations to league
     * of [[LeagueReturnDto]] type in pages
     *
     * @param page number of page
     * @param size size number
     * @return page of [[LeagueReturnDto]]
     */
    @GetMapping("/invitations")
    @ResponseStatus(HttpStatus.OK)
    public Page<LeagueReturnDto> getInvitations(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return leagueService.getLeagueInvitations(page, size);
    }

    /**
     * Method gathering all user leagues in pages
     *
     * @param page number of page
     * @param size size number
     * @return page of [[LeagueReturnDto]]
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<LeagueReturnDto> get(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return leagueService.getLeaguesByUser(page, size);
    }

    /**
     * Method deleting league of specified id
     * It requires ADMIN role
     *
     * @param leagueId id of league to delete
     * @return empty json response
     */
    @DeleteMapping("/{leagueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Long leagueId) {
        leagueService.delete(leagueId);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
