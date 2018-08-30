package pl.edu.agh.zti.dto.match;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Data transfer object of [[Match]]
 */
@Data
public class MatchDto {
    @NotNull
    private Long id;

    @NotNull
    private Date date;

    @NotNull
    private boolean finished;
}
