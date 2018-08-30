package pl.edu.agh.zti.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[Role]]
 */
@Data
public class RoleDto {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
