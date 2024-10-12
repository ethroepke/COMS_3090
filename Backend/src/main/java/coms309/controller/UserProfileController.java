
package coms309.controller;

import coms309.entity.UserProfile;
import coms309.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing user profiles.
 * 
 * Improvements:
 * - Added input validation for IDs.
 * - Enhanced error handling for user not found cases.
 * - Added logging for tracking operations.
 * - Improved code documentation.
 */
@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private final UserProfileRepository userProfileRepository;

    public UserProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    /**
     * Get all user profiles.
     * @return List of UserProfile
     */
    @GetMapping("/all")
    public List<UserProfile> getAllUserProfiles() {
        logger.info("Fetching all user profiles");
        return userProfileRepository.findAll();
    }

    /**
     * Get a user profile by its ID.
     * @param id ID of the user profile
     * @return ResponseEntity with UserProfile or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id) {
        logger.info("Fetching user profile with ID: {}", id);

        // Validate ID input
        if (id <= 0) {
            logger.warn("Invalid ID supplied: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<UserProfile> userProfile = userProfileRepository.findById(Math.toIntExact(id));

        return userProfile.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("User profile not found for ID: {}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }

    // Additional methods (POST, PUT, DELETE) can be similarly enhanced
}
