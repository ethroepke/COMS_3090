package onetoone.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 *
 * @author Vivek Bengre
 *
 */

public interface UserRepository extends JpaRepository<User, Long> {

    // Updated to return Optional for null safety and use Long for consistency with JpaRepository
    Optional<User> findById(Long id);

    @Transactional
    void deleteById(Long id);
}