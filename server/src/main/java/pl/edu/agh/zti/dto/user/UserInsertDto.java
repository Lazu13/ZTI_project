package pl.edu.agh.zti.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data transfer object of [[User]] from insert request
 */
@Data
public class UserInsertDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
