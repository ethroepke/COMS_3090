package coms309.repository;

import coms309.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
}
