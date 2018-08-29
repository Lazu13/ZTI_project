package pl.edu.agh.zti.schedulers.team;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Model representing file Team entity
 */
@Data
public class TeamJ {

    @NotNull
    private Long id;

    @NotNull
    private String name;

}
