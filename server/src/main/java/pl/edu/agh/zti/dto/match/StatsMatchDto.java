package pl.edu.agh.zti.dto.match;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[StatsMatch]]
 */
@Data
public class StatsMatchDto {
    @NotNull
    private MatchDto match;

    @NotNull
    private Integer resultH;

    @NotNull
    private Integer resultA;
}
