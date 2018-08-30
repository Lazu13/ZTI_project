package pl.edu.agh.zti.dto.user;

import lombok.Data;
import pl.edu.agh.zti.dto.RoleDto;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Data transfer object of [[User]] to return from query
 */
@Data
public class UserReturnDto {

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private Date createTime;

    @NotNull
    private RoleDto role;

}
