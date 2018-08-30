package pl.edu.agh.zti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.Group;

import java.util.Optional;

/**
 * Spring repository (data access object) for [[Group]] model
 */
@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

    Optional<Group> findById(Long id);

    Optional<Group> findByName(String name);

}
