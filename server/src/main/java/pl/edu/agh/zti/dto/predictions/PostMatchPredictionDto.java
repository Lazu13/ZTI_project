package pl.edu.agh.zti.dto.predictions;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[MatchPrediction]] from insert request
 */
@Data
public class PostMatchPredictionDto {
    @NotNull
    private Long matchId;

    @NotNull
    private int resultH;

    @NotNull
    private int resultA;

    @NotNull
    private boolean knockoutStage;

    private Long teamHId;

    private Long teamAId;
}
