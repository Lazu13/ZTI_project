package pl.edu.agh.zti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.zti.dto.match.GroupMatchDto;
import pl.edu.agh.zti.dto.match.KnockoutMatchDto;
import pl.edu.agh.zti.service.MatchService;

/**
 * Rest controller with request mapping of "/matches"
 */
@RestController
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;

    /**
     * Constructor with autowired argument
     *
     * @param matchService service processing match specific actions
     */
    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Method gathering all knockout matches
     *
     * @param page number of page
     * @param size size number
     * @return page of [[KnockoutMatchDto]]
     */
    @GetMapping("/all/knockout")
    @ResponseStatus(HttpStatus.OK)
    public Page<KnockoutMatchDto> getAll(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return matchService.getAllKnockoutMatches(page, size);
    }

    /**
     * Method gathering all group matches
     *
     * @param page number of page
     * @param size size number
     * @return page of [[GroupMatchDto]]
     */
    @GetMapping("/all/group")
    @ResponseStatus(HttpStatus.OK)
    public Page<GroupMatchDto> getAllGroup(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return matchService.getAllGroupMatches(page, size);
    }

}
