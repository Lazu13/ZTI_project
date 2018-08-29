package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.DeadlineDto;
import pl.edu.agh.zti.model.schedulers.Deadline;

/**
 * Spring component mapping [[Deadline]] to [[DeadlineDto]]
 */
@Component
public class DeadlineMapper {

    private ModelMapper modelMapper;
    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public DeadlineMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[DeadlineDto]] to [[Deadline]])
     *
     * @param dto data transfer object of [[DeadlineDto]]
     * @return Entity [[Deadline]]
     */
    public Deadline dtoToEntity(DeadlineDto dto) {
        return modelMapper.map(dto, Deadline.class);
    }

    /**
     * Method mapping Entity to DTO ([[Deadline]] to [[DeadlineDto]])
     *
     * @param user entity of [[Deadline]]
     * @return DTO [[DeadlineDto]]
     */
    public DeadlineDto entityToDto(Deadline user) {
        return modelMapper.map(user, DeadlineDto.class);
    }
}
