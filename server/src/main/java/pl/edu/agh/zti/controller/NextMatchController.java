package pl.edu.agh.zti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.zti.dto.NextMatchDto;
import pl.edu.agh.zti.service.NextMatchService;

/**
 * Rest controller with request mapping of "/next_match"
 */
@RestController
@RequestMapping("/next_match")
public class NextMatchController {

    private final NextMatchService nextMatchService;

    /**
     * Constructor with autowired argument
     *
     * @param nextMatchService service processing next_match specific actions
     */
    @Autowired
    public NextMatchController(NextMatchService nextMatchService) {
        this.nextMatchService = nextMatchService;
    }

    /**
     * Method gathering next match
     *
     * @return [[NextMatchDto]]
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public NextMatchDto get() {
        return nextMatchService.get();
    }
}
