
package coms309.repository;

import coms309.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing user profile data.
 * 
 * Improvements:
 * - Added better documentation for interface usage.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
}
