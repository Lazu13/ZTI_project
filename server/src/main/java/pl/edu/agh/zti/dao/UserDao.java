package pl.edu.agh.zti.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.zti.model.User;

import java.util.Optional;

/**
 * Spring repository (data access object) for [[User]] model
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Page<User> findByUsernameContaining(String username, Pageable pageable);

    Page<User> findByUsernameContainingAndActiveTrue(String username, Pageable pageable);

    Optional<User> findUserById(Long id);
}
