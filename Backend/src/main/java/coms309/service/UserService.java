package coms309.service;

import coms309.entity.User;
import coms309.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieve all users from the database.
     * @return List of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get a user by ID.
     * @param id User ID
     * @return Optional containing the user if found, empty otherwise
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Get a user by username.
     * @param username The username of the user
     * @return Optional containing the user if found, empty otherwise
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get a user by email.
     * @param email The email of the user
     * @return Optional containing the user if found, empty otherwise
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Create a new user.
     * @param user The user entity to create
     * @return The created user entity
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Update a user by their ID.
     * @param id The ID of the user to update
     * @param userDetails The updated user details
     * @return The updated user entity
     */
    public User updateUser(Long id, User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
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
    public User updateTimeWorked(Long id, Long timeWorked) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
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

    public boolean submitTimeForWeek(@Valid User user) {
        // Check if the user exists in the repository
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();

            // Update the user's time worked
            updatedUser.setTimeWorked(user.getTimeWorked());

            // Save the updated user back to the repository
            userRepository.save(updatedUser);
            return true;
        }
        return false; // User not found, return false
    }

    public boolean unsubmitTimeForWeek(@Valid User user) {
        // Check if the user exists in the repository
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();

            // Reset the timeWorked field to 0 or a default value
            updatedUser.setTimeWorked(0L);

            // Save the updated user back to the repository
            userRepository.save(updatedUser);
            return true;
        }
        return false; // User not found, return false
    }
}