package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.dao.RoleDao;
import pl.edu.agh.zti.model.Role;

/**
 * Spring service responsible for role-specific data loading
 */
@Service
public class RoleService {

    private final RoleDao roleDao;

    /**
     * Constructor with autowired arguments
     *
     * @param roleDao data access object of role
     */
    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * Method allowing to get role by its name
     *
     * @param name of role
     * @return role
     */
    @Loggable
    public Role findRoleByName(String name) {
        return roleDao
                .findRoleByName(name)
                .orElseThrow(RuntimeException::new);
    }
}
