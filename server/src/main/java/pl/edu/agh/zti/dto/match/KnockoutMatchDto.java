package pl.edu.agh.zti.dto.match;

import lombok.Data;
import pl.edu.agh.zti.dto.TeamDto;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[KnockoutMatch]]
 */
@Data
public class KnockoutMatchDto {
    @NotNull
    private MatchDto match;

    private StatsMatchDto statsMatch;

    private TeamDto teamH;

    private TeamDto teamA;

    @NotNull
    private String teamHDescription;

    @NotNull
    private String teamADescription;

    @NotNull
    private String stage;

}
