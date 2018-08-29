package pl.edu.agh.zti.schedulers.group;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Model representing file Group entity
 */
@Data
public class GroupJ {
    @NotNull
    private String name;
}
