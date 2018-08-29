package pl.edu.agh.zti.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.zti.dto.predictions.GetMatchPredictionDto;
import pl.edu.agh.zti.dto.predictions.PostMatchPredictionDto;
import pl.edu.agh.zti.model.EmptyJsonResponse;
import pl.edu.agh.zti.model.predictions.MatchPredictions;
import pl.edu.agh.zti.service.PredictionService;

/**
 * Rest controller with request mapping of "/predictions"
 */
@RestController
@RequestMapping("/predictions")
public class PredictionController {

    private final PredictionService predictionService;

    /**
     * Constructor with autowired argument
     *
     * @param predictionService service processing prediction specific actions
     */
    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    /**
     * Method gathering user predictions
     *
     * @param page number of page
     * @param size size number
     * @return page of [[GetMatchPredictionDto]]
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<GetMatchPredictionDto> get(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return predictionService.getByCurrentUser(page, size);
    }

    /**
     * Method gathering specified user predictions
     *
     * @param page number of page
     * @param size size number
     * @param id id of user
     * @return page of [[GetMatchPredictionDto]]
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<GetMatchPredictionDto> getByUser(
            @RequestParam int page,
            @RequestParam int size,
            @PathVariable Long id
    ) {
        return predictionService.getByUser(id, page, size);
    }

    /**
     * Method making new prediction for current user
     *
     * @param predictionDto prediction data
     * @return empty json response
     */
    @PostMapping("/make")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity predict(
            @RequestBody PostMatchPredictionDto predictionDto
    ) {
        predictionService.makePrediction(predictionDto);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    /**
     * Method making many predictions at once for current user
     *
     * @param predictionDtos many prediction data
     * @return empty json response
     */
    @PostMapping("/make/many")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity predict(
            @RequestBody MatchPredictions predictionDtos
    ) {
        predictionService.makePredictions(predictionDtos);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}