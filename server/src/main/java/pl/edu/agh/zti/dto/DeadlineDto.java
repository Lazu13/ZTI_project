package pl.edu.agh.zti.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Data transfer object of [[Deadline]]
 */
@Data
public class DeadlineDto {
    @NotNull
    private Date date;
}
