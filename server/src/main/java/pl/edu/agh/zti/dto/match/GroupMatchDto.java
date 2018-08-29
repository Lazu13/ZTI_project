package pl.edu.agh.zti.dto.match;

import lombok.Data;
import pl.edu.agh.zti.dto.GroupDto;
import pl.edu.agh.zti.dto.TeamDto;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[GroupMatch]]
 */
@Data
public class GroupMatchDto {
    @NotNull
    private MatchDto match;

    @NotNull
    private GroupDto group;

    private StatsMatchDto statsMatch;

    @NotNull
    private TeamDto teamA;

    @NotNull
    private TeamDto teamH;

}
