package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.deadline.DeadlineAfterCheckerable;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.config.SessionBean;
import pl.edu.agh.zti.dao.UserDao;
import pl.edu.agh.zti.dto.user.UserInsertDto;
import pl.edu.agh.zti.dto.user.UserReturnDto;
import pl.edu.agh.zti.exceptions.NotFoundException;
import pl.edu.agh.zti.mapper.UserMapper;
import pl.edu.agh.zti.model.User;

import javax.transaction.Transactional;

/**
 * Spring service responsible for user-specific data loading
 */
@Service
@Transactional
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final UserLeagueService userLeagueService;
    private final SessionBean sessionBean;

    /**
     * Constructor with autowired arguments
     *
     * @param userDao data access object of deadline
     * @param userMapper user mapper
     * @param roleService role service
     * @param userLeagueService user league service
     * @param sessionBean current session bean
     */
    @Autowired
    public UserService(
            UserDao userDao,
            UserMapper userMapper,
            RoleService roleService,
            UserLeagueService userLeagueService,
            SessionBean sessionBean) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.userLeagueService = userLeagueService;
        this.sessionBean = sessionBean;
    }

    /**
     * Method allowing to save new user to database
     *
     * @param userInsertDto data of user to save
     */
    @Transactional
    @Loggable
    @DeadlineAfterCheckerable
    public void save(UserInsertDto userInsertDto) {
        User user = userMapper.dtoToEntity(userInsertDto);
        user.setRole(roleService.findRoleByName("USER"));
        userDao.save(user);
        Long globalLeagueId = 1L;
        userLeagueService.invite(globalLeagueId, user.getId(), true);
    }

    /**
     * Method allowing to gather current user
     *
     * @return current user
     */
    @Loggable
    public UserReturnDto get() {
        User user = sessionBean.getCurrentUser();
        return userMapper.entityToDto(user);
    }

    /**
     * Method allowing to gather user based on username like
     *
     * @param username part of username
     * @param page number of page
     * @param size size number
     * @return page of found users
     */
    @Loggable
    public Page<UserReturnDto> getByUsernameLike(String username, int page, int size) {
        Page<User> users = userDao.findByUsernameContainingAndActiveTrue(username, new PageRequest(page, size));
        return users.map(user -> userMapper.entityToDto(user));
    }

    /**
     * Method allowing to delete user
     *
     * @param id of user to delete
     */
    @Loggable
    public void delete(Long id) {
        User user = userDao.findUserById(id).orElseThrow(NotFoundException::new);
        if (user.getRole().getId() != 1) {
            user.setActive(false);
            userDao.save(user);
        } else throw new NotFoundException();
    }
}
