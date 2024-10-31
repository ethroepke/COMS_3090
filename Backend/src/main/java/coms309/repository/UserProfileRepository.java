package coms309.repository;

import coms309.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUsernameAndPassword (@Param("user_name") String username, @Param("password") String password);
    Optional<UserProfile> findByUsername(String username);
    Optional<UserProfile> findByEmail(String email);
}
