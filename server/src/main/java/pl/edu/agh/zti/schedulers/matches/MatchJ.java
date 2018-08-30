package pl.edu.agh.zti.schedulers.matches;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Model representing file Match entity
 */
@Data
public class MatchJ {
    @NotNull
    private Long name;

    @NotNull
    private Date date;

    @NotNull
    private long home_team;

    @NotNull
    private long away_team;

    @NotNull
    private int home_result;

    @NotNull
    private int away_result;

    private String teamHDescription;

    private String teamADescription;

    @NotNull
    private boolean finished = false;
}
