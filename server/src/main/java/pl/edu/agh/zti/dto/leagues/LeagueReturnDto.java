package pl.edu.agh.zti.dto.leagues;

import lombok.Data;
import pl.edu.agh.zti.dto.UserLeagueDto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Data transfer object of League to return from query
 */
@Data
public class LeagueReturnDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private List<UserLeagueDto> participants;

    @NotNull
    private boolean active;
}
