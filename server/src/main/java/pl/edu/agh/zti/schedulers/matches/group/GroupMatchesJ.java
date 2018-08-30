package pl.edu.agh.zti.schedulers.matches.group;

import lombok.Data;
import pl.edu.agh.zti.schedulers.matches.MatchJ;

import javax.validation.constraints.NotNull;

/**
 * Model representing file group match entity
 */
@Data
public class GroupMatchesJ {

    @NotNull
    private String name;

    @NotNull
    private MatchJ[] matches;

}
