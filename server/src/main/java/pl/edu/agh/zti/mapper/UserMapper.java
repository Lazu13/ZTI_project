package pl.edu.agh.zti.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dto.user.UserInsertDto;
import pl.edu.agh.zti.dto.user.UserReturnDto;
import pl.edu.agh.zti.model.User;

/**
 * Spring component mapping [[User]] to [[UserDto]]
 */
@Component
public class UserMapper {

    private ModelMapper modelMapper;

    /**
     * Constructor with autowired argument
     *
     * @param modelMapper mapper performing object mapping
     */
    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Method mapping DTO to Entity ([[UserInsertDto]] to [[User]])
     *
     * @param dto data transfer object of [[UserInsertDto]]
     * @return Entity [[User]]
     */
    public User dtoToEntity(UserInsertDto dto) {
        User user = modelMapper.map(dto, User.class);
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(encryptedPassword);
        return user;
    }

    /**
     * Method mapping Entity to DTO ([[User]] to [[UserReturnDto]])
     *
     * @param user entity of [[User]]
     * @return DTO [[UserReturnDto]]
     */
    public UserReturnDto entityToDto(User user) {
        return modelMapper.map(user, UserReturnDto.class);
    }
}
