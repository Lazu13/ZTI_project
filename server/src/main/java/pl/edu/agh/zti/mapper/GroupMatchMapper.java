package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.match.GroupMatchDto;
import pl.edu.agh.zti.model.match.GroupMatch;

/**
 * Spring component mapping [[GroupMatch]] to [[GroupMatchDto]]
 */
@Component
public class GroupMatchMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public GroupMatchMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[GroupMatchDto]] to [[GroupMatch]])
     *
     * @param dto data transfer object of [[GroupMatchDto]]
     * @return Entity [[GroupMatchDto]]
     */
    public GroupMatch dtoToEntity(GroupMatchDto dto) {
        return modelMapper.map(dto, GroupMatch.class);
    }

    /**
     * Method mapping Entity to DTO ([[GroupMatch]] to [[GroupMatchDto]])
     *
     * @param user entity of [[GroupMatch]]
     * @return DTO [[GroupMatchDto]]
     */
    public GroupMatchDto entityToDto(GroupMatch user) {
        return modelMapper.map(user, GroupMatchDto.class);
    }
}
