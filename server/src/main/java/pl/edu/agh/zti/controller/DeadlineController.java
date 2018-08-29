package pl.edu.agh.zti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.zti.dto.DeadlineDto;
import pl.edu.agh.zti.service.DeadlineService;

/**
 * Rest controller with request mapping of "/deadline"
 */
@RestController
@RequestMapping("/deadline")
public class DeadlineController {

    private final DeadlineService deadlineService;

    /**
     * Constructor with autowired argument
     *
     * @param deadlineService service processing deadline specific actions
     */
    @Autowired
    public DeadlineController(DeadlineService deadlineService) {
        this.deadlineService = deadlineService;
    }

    /**
     * GET Method allowing to get deadline.
     *
     * @return data transfer object of deadline with [[HttpStatus.OK]
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DeadlineDto get() {
        return deadlineService.get();
    }

}
