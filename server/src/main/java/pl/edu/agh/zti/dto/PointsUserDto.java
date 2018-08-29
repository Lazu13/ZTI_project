package pl.edu.agh.zti.dto;

import lombok.Data;
import pl.edu.agh.zti.dto.match.MatchDto;
import pl.edu.agh.zti.dto.user.UserReturnDto;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[PointsUser]]
 */
@Data
public class PointsUserDto {
    @NotNull
    private UserReturnDto user;

    @NotNull
    private MatchDto match;

    @NotNull
    private Long points;

    @NotNull
    private Long totalPoints;
}
