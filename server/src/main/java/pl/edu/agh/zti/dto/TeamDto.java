package pl.edu.agh.zti.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[Team]]
 */
@Data
public class TeamDto {
    @NotNull
    private Long id;

    @NotNull
    private String name;
}
