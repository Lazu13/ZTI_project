package pl.edu.agh.zti.schedulers.group;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.model.Group;

/**
 * Spring component mapping [[GroupJ]] to [[Group]]] and [[Group[]]]
 */
@Component
public class GroupJMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public GroupJMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[GroupJ]] to [[Group]])
     *
     * @param dto data transfer object of [[GroupJ]]
     * @return Entity [[Group]]
     */
    public Group jToEntity(GroupJ dto) {
        return modelMapper.map(dto, Group.class);
    }

    /**
     * Method mapping Entity to DTO ([[Group]] to [[GroupJ]])
     *
     * @param user entity of [[Group]]
     * @return DTO [[GroupJ]]
     */
    public GroupJ entityToJ(Group user) {
        return modelMapper.map(user, GroupJ.class);
    }

    /**
     * Method mapping DTO to Entity ([[GroupJ[]]] to [[Group[]]])
     *
     * @param dtos data transfer object of [[GroupJ[]]]
     * @return Entity [[Group[]]]
     */
    public Group[] jsToEntities(GroupJ[] dtos) {
        return modelMapper.map(dtos, Group[].class);
    }

    /**
     * Method mapping Entity to DTO ([[Group[]]] to [[GroupJ[]]])
     *
     * @param users entity of [[Group[]]]
     * @return DTO [[GroupJ[]]]
     */
    public GroupJ[] entitiesToJs(Group[] users) {
        return modelMapper.map(users, GroupJ[].class);
    }
}
