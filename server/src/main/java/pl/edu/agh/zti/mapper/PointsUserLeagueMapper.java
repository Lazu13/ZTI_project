package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.PointsUserDto;
import pl.edu.agh.zti.model.predictions.PointsUser;

/**
 * Spring component mapping [[PointsUser]] to [[PointsUserDto]]
 */
@Component
public class PointsUserLeagueMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public PointsUserLeagueMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[PointsUserDto]] to [[PointsUser]])
     *
     * @param dto data transfer object of [[PointsUserDto]]
     * @return Entity [[PointsUser]]
     */
    public PointsUser dtoToEntity(PointsUserDto dto) {
        return modelMapper.map(dto, PointsUser.class);
    }

    /**
     * Method mapping Entity to DTO ([[PointsUser]] to [[PointsUserDto]])
     *
     * @param user entity of [[PointsUser]]
     * @return DTO [[PointsUserDto]]
     */
    public PointsUserDto entityToDto(PointsUser user) {
        return modelMapper.map(user, PointsUserDto.class);
    }
}
