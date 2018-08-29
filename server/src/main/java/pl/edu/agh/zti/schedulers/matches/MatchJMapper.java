package pl.edu.agh.zti.schedulers.matches;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.model.match.Match;

/**
 * Spring component mapping [[MatchJ]] to [[Match]] and [[Match[]]]
 */
@Component
public class MatchJMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public MatchJMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[MatchJ]] to [[Match]])
     *
     * @param j data transfer object of [[MatchJ]]
     * @return Entity [[Match]]
     */
    public Match jToEntity(MatchJ j) {
        return modelMapper.map(j, Match.class);
    }

    /**
     * Method mapping Entity to DTO ([[Match]] to [[MatchJ]])
     *
     * @param user entity of [[Match]]
     * @return DTO [[MatchJ]]
     */
    public MatchJ entityToJ(Match user) {
        return modelMapper.map(user, MatchJ.class);
    }

    /**
     * Method mapping DTO to Entity ([[MatchJ[]]] to [[Match[]]])
     *
     * @param dtos data transfer object of [[MatchJ[]]]
     * @return Entity [[Match[]]]
     */
    public Match[] jsToEntities(MatchJ[] dtos) {
        return modelMapper.map(dtos, Match[].class);
    }

    /**
     * Method mapping Entity to DTO ([[Match[]]] to [[MatchJ[]]])
     *
     * @param users entity of [[Match[]]]
     * @return DTO [[MatchJ[]]]
     */
    public MatchJ[] entitiesToJs(Match[] users) {
        return modelMapper.map(users, MatchJ[].class);
    }
}
