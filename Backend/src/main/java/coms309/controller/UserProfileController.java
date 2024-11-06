package coms309.controller;

import coms309.dto.SignUpDTO;
import coms309.dto.UserDTO;
import coms309.dto.SignUpDTO;
import coms309.entity.UserProfile;
import coms309.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// Import other necessary packages
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserService userService;

    /**
     * Retrieve all user profiles.
     *
     * @return List of all users.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserProfile>> getAllUserProfiles() {
        logger.info("Controller: Fetching all user profiles");
        List<UserProfile> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieve a user profile by ID.
     *
     * @param id The user ID.
     * @return The user profile if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id) {
        logger.info("Controller: Fetching user profile with ID: {}", id);
        Optional<UserProfile> userProfileOpt = userService.getUserById(id);
        return userProfileOpt.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Controller: User profile not found for ID: {}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }

    /**
     * Retrieve a user profile by username.
     *
     * @param username The username of the user.
     * @return The user profile if found.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserProfile> getUserProfileByUsername(@PathVariable String username) {
        logger.info("Controller: Fetching user profile with username: {}", username);
        Optional<UserProfile> userProfileOpt = userService.getUserByUsername(username);
        return userProfileOpt.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Controller: User profile not found for username: {}", username);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }

    /**
     * Retrieve a user profile by email.
     *
     * @param email The email of the user.
     * @return The user profile if found.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserProfile> getUserProfileByEmail(@PathVariable String email) {
        logger.info("Controller: Fetching user profile with email: {}", email);
        Optional<UserProfile> userProfileOpt = userService.getUserByEmail(email);
        return userProfileOpt.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Controller: User profile not found for email: {}", email);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }

    /**
     * Create a new user profile.
     *
     * @param user The user profile to create.
     * @return The created user profile.
     */
    @PostMapping("/create")
    public ResponseEntity<UserProfile> createUserProfile( @RequestBody UserProfile user) {
        logger.info("Controller: Creating new user profile");
        UserProfile createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Update an existing user profile by ID.
     *
     * @param id   The ID of the user to update.
     * @param user The updated user profile details.
     * @return The updated user profile if successful.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UserProfile user) {
        logger.info("Controller: Updating user profile with ID: {}", id);
        UserProfile updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            logger.warn("Controller: User profile not found for ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Update the time worked for a user by ID.
     *
     * @param id         The ID of the user.
     * @param timeWorked The new time worked value.
     * @return The updated user profile if successful.
     */
    @PutMapping("/{id}/timeWorked")
    public ResponseEntity<UserProfile> updateTimeWorked(
            @PathVariable Long id,
            @RequestParam Integer timeWorked) {
        logger.info("Controller: Updating time worked for user ID: {}", id);
        UserProfile updatedUser = userService.updateTimeWorked(id, timeWorked);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            logger.warn("Controller: User profile not found for ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Delete a user profile by ID.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity indicating the outcome.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        logger.info("Controller: Deleting user profile with ID: {}", id);
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Controller: User profile not found for ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Submit time for a week for a user.
     *
     * @param user The user profile with updated time.
     * @return ResponseEntity indicating the outcome.
     */
    @PostMapping("/submitTime")
    public ResponseEntity<String> submitTimeForWeek(@Valid @RequestBody UserProfile user) {
        logger.info("Controller: Submitting time for week for user ID: {}", user.getUserId());
        boolean success = userService.submitTimeForWeek(user);
        if (success) {
            return ResponseEntity.ok("Time submitted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    /**
     * Unsubmit time for a week for a user.
     *
     * @param user The user profile.
     * @return ResponseEntity indicating the outcome.
     */
    @PostMapping("/unsubmitTime")
    public ResponseEntity<String> unsubmitTimeForWeek(@Valid @RequestBody UserProfile user) {
        logger.info("Controller: Unsubmitting time for week for user ID: {}", user.getUserId());
        boolean success = userService.unsubmitTimeForWeek(user);
        if (success) {
            return ResponseEntity.ok("Time unsubitted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    /**
     * Retrieve the next shift for a user.
     *
     * @param userId The ID of the user.
     * @return The next shift details if found.
     */
    @GetMapping("/{userId}/nextShift")
    public ResponseEntity<String> getNextShift(@PathVariable Long userId) {
        logger.info("Controller: Retrieving next shift for user ID: {}", userId);
        return userService.getNextShift(userId);
    }

    /**
     * Retrieve the time worked for a user.
     *
     * @param userId The ID of the user.
     * @return The time worked details if found.
     */
    @GetMapping("/{userId}/timeWorked")
    public ResponseEntity<String> getTimeWorked(@PathVariable Long userId) {
        logger.info("Controller: Retrieving time worked for user ID: {}", userId);
        return userService.getTimeWorked(userId);
    }

    /**
     * Handle password reset for a user.
     *
     * @param forgotUserDTO DTO containing email and new password.
     * @return ResponseEntity indicating the outcome.
     */
    @PutMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody UserDTO forgotUserDTO) {
        logger.info("Controller: Attempting to reset password for email: {}", forgotUserDTO.getEmail());
        return userService.forgotPassword(forgotUserDTO);
    }

    /**
     * Handle user login.
     *
     * @param loginUserDTO DTO containing username and password.
     * @return ResponseEntity indicating the outcome.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login( @RequestBody UserDTO loginUserDTO) {
        logger.info("Controller: Attempting login for username: {}", loginUserDTO.getUsername());
        return userService.login(loginUserDTO);
    }

    /**
     * Handle user signup.
     *
     * @param signUpUserProfileDTO DTO containing signup information.
     * @return ResponseEntity indicating the outcome.
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpDTO signUpUserProfileDTO) {
        logger.info("Controller: Attempting signup for username: {}", signUpUserProfileDTO.getUsername());
        return userService.signup(signUpUserProfileDTO);
    }

}
