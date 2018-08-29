package pl.edu.agh.zti.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[Group]]
 */
@Data
public class GroupDto {
    @NotNull
    private String name;
}
