package pl.edu.agh.zti.dto.predictions;

import lombok.Data;
import pl.edu.agh.zti.dto.TeamDto;
import pl.edu.agh.zti.dto.match.MatchDto;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[MatchPrediction]] to return from query
 */
@Data
public class GetMatchPredictionDto {
    @NotNull
    private MatchDto match;

    @NotNull
    private int resultH;

    @NotNull
    private int resultA;

    @NotNull
    private boolean knockoutStage;

    private TeamDto teamH;

    private TeamDto teamA;
}
