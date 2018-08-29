package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.leagues.LeagueInsertDto;
import pl.edu.agh.zti.dto.leagues.LeagueReturnDto;
import pl.edu.agh.zti.model.League;

/**
 * Spring component mapping [[League]] to [[LeagueReturnDto]] and [[LeagueInsertDto]]
 */
@Component
public class LeagueMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public LeagueMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[LeagueReturnDto]] to [[League]])
     *
     * @param dto data transfer object of [[LeagueReturnDto]]
     * @return Entity [[League]]
     */
    public League dtoToEntity(LeagueReturnDto dto) {
        return modelMapper.map(dto, League.class);
    }

    /**
     * Method mapping Entity to DTO ([[League]] to [[LeagueReturnDto]])
     *
     * @param league entity of [[League]]
     * @return DTO [[LeagueReturnDto]]
     */
    public LeagueReturnDto entityToDto(League league) {
        return modelMapper.map(league, LeagueReturnDto.class);
    }

    /**
     * Method mapping DTO to Entity ([[LeagueInsertDto]] to [[League]])
     *
     * @param dto data transfer object of [[LeagueInsertDto]]
     * @return Entity [[League]]
     */
    public League dtoToEntity(LeagueInsertDto dto) {
        return modelMapper.map(dto, League.class);
    }

}
