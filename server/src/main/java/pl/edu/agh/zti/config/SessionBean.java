package pl.edu.agh.zti.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dao.UserDao;
import pl.edu.agh.zti.exceptions.NotFoundException;
import pl.edu.agh.zti.model.User;

/**
 * Spring component for session bean
 */
@Component
public class SessionBean {

    private final UserDao userDao;

    /**
     * @param userDao data access object for user
     */
    @Autowired
    public SessionBean(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Method allowing to gather current user from principal of security context's authentication
     *
     * @return current user
     * @throws NotFoundException if there isn't such user
     */
    public User getCurrentUser() throws NotFoundException {
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userDao.findUserByUsername(principal)
                .orElseThrow(NotFoundException::new);
    }
}
