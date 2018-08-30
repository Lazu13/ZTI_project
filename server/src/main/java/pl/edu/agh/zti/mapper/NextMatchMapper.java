package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.NextMatchDto;
import pl.edu.agh.zti.model.schedulers.NextMatch;

/**
 * Spring component mapping [[NextMatch]] to [[NextMatchDto]]
 */
@Component
public class NextMatchMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public NextMatchMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[NextMatchDto]] to [[NextMatch]])
     *
     * @param dto data transfer object of [[NextMatchDto]]
     * @return Entity [[NextMatch]]
     */

    public NextMatch dtoToEntity(NextMatchDto dto) {
        return modelMapper.map(dto, NextMatch.class);
    }

    /**
     * Method mapping Entity to DTO ([[NextMatch]] to [[NextMatchDto]])
     *
     * @param user entity of [[NextMatch]]
     * @return DTO [[NextMatchDto]]
     */

    public NextMatchDto entityToDto(NextMatch user) {
        return modelMapper.map(user, NextMatchDto.class);
    }
}
