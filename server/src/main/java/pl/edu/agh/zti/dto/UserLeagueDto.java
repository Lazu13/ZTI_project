package pl.edu.agh.zti.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.edu.agh.zti.dto.leagues.LeagueReturnDto;
import pl.edu.agh.zti.dto.user.UserReturnDto;
import pl.edu.agh.zti.enums.UserLeagueStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Data transfer object of [[UserLeague]]
 */
@Data
public class UserLeagueDto {
    @Enumerated(value = EnumType.STRING)
    private UserLeagueStatus status;

    private UserReturnDto user;

    @JsonIgnore
    private LeagueReturnDto league;
}
