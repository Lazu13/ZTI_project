package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.match.KnockoutMatchDto;
import pl.edu.agh.zti.model.match.KnockoutMatch;

/**
 * Spring component mapping [[KnockoutMatch]] to [[KnockoutMatchDto]]
 */
@Component
public class KnockoutMatchMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public KnockoutMatchMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[KnockoutMatchDto]] to [[KnockoutMatch]])
     *
     * @param dto data transfer object of [[KnockoutMatchDto]]
     * @return Entity [[KnockoutMatch]]
     */
    public KnockoutMatch dtoToEntity(KnockoutMatchDto dto) {
        return modelMapper.map(dto, KnockoutMatch.class);
    }

    /**
     * Method mapping Entity to DTO ([[KnockoutMatch]] to [[KnockoutMatchDto]])
     *
     * @param user entity of [[KnockoutMatchDto]]
     * @return DTO [[KnockoutMatchDto]]
     */
    public KnockoutMatchDto entityToDto(KnockoutMatch user) {
        return modelMapper.map(user, KnockoutMatchDto.class);
    }
}
