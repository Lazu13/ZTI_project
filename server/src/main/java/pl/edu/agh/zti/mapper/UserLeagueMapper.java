package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.UserLeagueDto;
import pl.edu.agh.zti.model.UserLeague;

/**
 * Spring component mapping [[UserLeague]] to [[UserLeagueDto]]
 */
@Component
public class UserLeagueMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public UserLeagueMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[UserLeagueDto]] to [[UserLeague]])
     *
     * @param dto data transfer object of [[UserLeagueDto]]
     * @return Entity [[UserLeague]]
     */
    public UserLeague dtoToEntity(UserLeagueDto dto) {
        return modelMapper.map(dto, UserLeague.class);
    }

    /**
     * Method mapping Entity to DTO ([[UserLeagueDto]] to [[User]])
     *
     * @param user entity of [[UserLeague]]
     * @return DTO [[UserLeagueDto]]
     */
    public UserLeagueDto entityToDto(UserLeague user) {
        return modelMapper.map(user, UserLeagueDto.class);
    }
}
