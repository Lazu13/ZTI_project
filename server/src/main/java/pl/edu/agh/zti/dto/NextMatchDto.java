package pl.edu.agh.zti.dto;

import lombok.Data;
import pl.edu.agh.zti.dto.match.MatchDto;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[NextMatch]]
 */
@Data
public class NextMatchDto {

    @NotNull
    private MatchDto match;
}
