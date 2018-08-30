package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.Role;

import java.util.Optional;

/**
 * Spring repository (data access object) for [[Role]] model
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
