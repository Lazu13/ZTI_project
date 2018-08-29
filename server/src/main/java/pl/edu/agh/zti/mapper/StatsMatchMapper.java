package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.match.StatsMatchDto;
import pl.edu.agh.zti.model.match.StatsMatch;

/**
 * Spring component mapping [[StatsMatch]] to [[StatsMatchDto]]
 */
@Component
public class StatsMatchMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public StatsMatchMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[StatsMatchDto]] to [[StatsMatch]])
     *
     * @param dto data transfer object of [[StatsMatchDto]]
     * @return Entity [[StatsMatch]]
     */
    public StatsMatch dtoToEntity(StatsMatchDto dto) {
        return modelMapper.map(dto, StatsMatch.class);
    }

    /**
     * Method mapping Entity to DTO ([[StatsMatch]] to [[StatsMatchDto]])
     *
     * @param user entity of [[StatsMatch]]
     * @return DTO [[StatsMatchDto]]
     */
    public StatsMatchDto entityToDto(StatsMatch user) {
        return modelMapper.map(user, StatsMatchDto.class);
    }
}
