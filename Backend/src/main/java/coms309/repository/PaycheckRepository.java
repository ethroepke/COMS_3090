
package coms309.repository;

import coms309.entity.Paycheck;
import coms309.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaycheckRepository extends JpaRepository<Paycheck, Long> {
    Optional<Paycheck> findByUserProfile(UserProfile userProfile);
    List<Paycheck> findAllByUserProfile(UserProfile userProfile);
}
