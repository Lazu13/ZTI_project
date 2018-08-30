package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.predictions.GetMatchPredictionDto;
import pl.edu.agh.zti.dto.predictions.PostMatchPredictionDto;
import pl.edu.agh.zti.model.predictions.MatchPrediction;

/**
 * Spring component mapping [[MatchPrediction]] to [[GetMatchPredictionDto]] and [[PostMatchPrediction]]
 */
@Component
public class MatchPredictionMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public MatchPredictionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    /**
     * Method mapping DTO to Entity ([[GetMatchPredictionDto]] to [[MatchPrediction]])
     *
     * @param dto data transfer object of [[GetMatchPredictionDto]]
     * @return Entity [[MatchPrediction]]
     */
    public MatchPrediction dtoToEntity(GetMatchPredictionDto dto) {
        return modelMapper.map(dto, MatchPrediction.class);
    }

    /**
     * Method mapping Entity to DTO ([[MatchPrediction]] to [[GetMatchPredictionDto]])
     *
     * @param matchPrediction entity of [[MatchPrediction]]
     * @return DTO [[GetMatchPredictionDto]]
     */
    public GetMatchPredictionDto getEntityToDto(MatchPrediction matchPrediction) {
        return modelMapper.map(matchPrediction, GetMatchPredictionDto.class);
    }


    /**
     * Method mapping DTO to Entity ([[PostMatchPrediction]] to [[MatchPrediction]])
     *
     * @param dto data transfer object of [[PostMatchPrediction]]
     * @return Entity [[MatchPrediction]]
     */
    public MatchPrediction dtoToEntity(PostMatchPredictionDto dto) {
        return modelMapper.map(dto, MatchPrediction.class);
    }

    /**
     * Method mapping Entity to DTO ([[MatchPrediction]] to [[PostMatchPrediction]])
     *
     * @param matchPrediction entity of [[MatchPrediction]]
     * @return DTO [[PostMatchPrediction]]
     */
    public PostMatchPredictionDto postEntityToDto(MatchPrediction matchPrediction) {
        return modelMapper.map(matchPrediction, PostMatchPredictionDto.class);
    }
}
