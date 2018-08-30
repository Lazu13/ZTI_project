package pl.edu.agh.zti.schedulers.matches.knockout;

import lombok.Data;
import pl.edu.agh.zti.schedulers.matches.MatchJ;

import javax.validation.constraints.NotNull;

/**
 * Model representing file knockout match entity
 */
@Data
public class KnockoutJ {

    @NotNull
    private String name;

    @NotNull
    private MatchJ[] matches;

}
