package pl.edu.agh.zti.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.dao.UserDao;
import pl.edu.agh.zti.exceptions.BadCredentialsException;
import pl.edu.agh.zti.model.User;

import javax.transaction.Transactional;

/**
 * Spring service responsible for user-specific data loading
 */
@Service
@Primary
public class UserCredentialsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    /**
     * Constructor with autowired arguments
     *
     * @param userDao data access object for user
     */
    @Autowired
    public UserCredentialsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Overridden method for user loading based on username, using user DAO
     *
     * @param username name of user
     * @return core user information
     * @throws UsernameNotFoundException if there isn't user with specified name
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username)
                .orElseThrow(BadCredentialsException::new);
        return new UserCredentials(user);
    }
}