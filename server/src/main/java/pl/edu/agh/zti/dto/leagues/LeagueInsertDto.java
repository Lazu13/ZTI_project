package pl.edu.agh.zti.dto.leagues;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of League from insert request
 */
@Data
public class LeagueInsertDto {

    @NotNull
    private String name;
}
