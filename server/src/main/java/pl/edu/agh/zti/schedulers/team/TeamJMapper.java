package pl.edu.agh.zti.schedulers.team;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.model.Team;

/**
 * Spring component mapping [[TeamJ]] to [[Team]] and [[Team[]]]
 */
@Component
public class TeamJMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public TeamJMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[TeamJ]] to [[Team]])
     *
     * @param j data transfer object of [[TeamJ]]
     * @return Entity [[Team]]
     */
    public Team jToEntity(TeamJ j) {
        return modelMapper.map(j, Team.class);
    }

    /**
     * Method mapping Entity to DTO ([[Team]] to [[TeamJ]])
     *
     * @param user entity of [[Team]]
     * @return DTO [[TeamJ]]
     */
    public TeamJ entityToJ(Team user) {
        return modelMapper.map(user, TeamJ.class);
    }

    /**
     * Method mapping DTO to Entity ([[TeamJ[]]] to [[Team[]]])
     *
     * @param dtos data transfer object of [[TeamJ[]]]
     * @return Entity [[Team[]]]
     */
    public Team[] jsToEntities(TeamJ[] dtos) {
        return modelMapper.map(dtos, Team[].class);
    }

    /**
     * Method mapping Entity to DTO ([[Team[]]] to [[TeamJ[]]])
     *
     * @param users entity of [[Team[]]]
     * @return DTO [[TeamJ[]]]
     */
    public TeamJ[] entitiesToJs(Team[] users) {
        return modelMapper.map(users, TeamJ[].class);
    }
}
