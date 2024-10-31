package coms309.service;

import coms309.entity.UserProfile;
import coms309.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserProfileRepository userRepository;

    /**
     * Retrieve all users from the database.
     * @return List of users
     */
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }



    /**
     * Get a user by ID.
     * @param id User ID
     * @return Optional containing the user if found, empty otherwise
     */
    public Optional<UserProfile> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Get a user by username.
     * @param username The username of the user
     * @return Optional containing the user if found, empty otherwise
     */
    public Optional<UserProfile> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get a user by email.
     * @param email The email of the user
     * @return Optional containing the user if found, empty otherwise
     */
    public Optional<UserProfile> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Create a new user.
     * @param user The user entity to create
     * @return The created user entity
     */
    public UserProfile createUser(UserProfile user) {
        return userRepository.save(user);
    }

    /**
     * Update a user by their ID.
     * @param id The ID of the user to update
     * @param userDetails The updated user details
     * @return The updated user entity
     */
    public UserProfile updateUser(Long id, UserProfile userDetails) {
        Optional<UserProfile> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserProfile user = existingUser.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setUserType(userDetails.getUserType());
            user.setTimeWorked(userDetails.getTimeWorked());  // Set the updated timeWorked
            return userRepository.save(user);
        }
        return null; // Handle better error handling
    }

    /**
     * Update the time worked for a user by their ID.
     * @param id The user ID
     * @param timeWorked The updated time worked value
     * @return The updated user entity, or null if the user is not found
     */
    public UserProfile updateTimeWorked(Long id, int timeWorked) {
        Optional<UserProfile> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserProfile user = existingUser.get();
            user.setTimeWorked(timeWorked);  // Update timeWorked field
            return userRepository.save(user);
        }
        return null; // Handle better error handling
    }

    /**
     * Delete a user by their ID.
     * @param id The ID of the user to delete
     * @return True if the user was deleted, false if not found
     */
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean submitTimeForWeek(@Valid UserProfile user) {
        // Check if the user exists in the repository
        Optional<UserProfile> existingUser = userRepository.findById(user.getUserId());
        if (existingUser.isPresent()) {
            UserProfile updatedUser = existingUser.get();

            // Update the user's time worked
            updatedUser.setTimeWorked(user.getTimeWorked());

            // Save the updated user back to the repository
            userRepository.save(updatedUser);
            return true;
        }
        return false; // User not found, return false
    }

    public boolean unsubmitTimeForWeek(@Valid UserProfile user) {
        // Check if the user exists in the repository
        Optional<UserProfile> existingUser =  userRepository.findById(user.getUserId());
        if (existingUser.isPresent()) {
            UserProfile updatedUser = existingUser.get();

            // Reset the timeWorked field to 0 or a default value
            updatedUser.setTimeWorked(0);

            // Save the updated user back to the repository
            userRepository.save(updatedUser);
            return true;
        }
        return false; // User not found, return false
    }

    public ResponseEntity<String> getNextShift(Long userId) {
        Optional<UserProfile> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserProfile userProfile = user.get();
            String message = "Next shift for user: " + userProfile.getNextShift();
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> getTimeWorked(Long userId) {
        Optional<UserProfile> user = userRepository .findById(userId);
        if (user.isPresent()) {
            UserProfile userProfile = user.get();
            String message = "Hours worked for user: " + userProfile.getTimeWorked();
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.notFound().build();
    }
}