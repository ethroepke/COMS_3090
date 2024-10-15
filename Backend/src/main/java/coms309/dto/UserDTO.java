package coms309.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class UserDTO {
    private Long userId;
    private String username;

    @Nullable
    private String email;

    private String password;

    public UserDTO(Long userId, String username, String email, String password) {
        // Log constructor usage
        System.out.println("Creating UserDTO for userId: " + userId);

        // Validate userId (ensure it's not null or less than 1)
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID cannot be null or less than 1");
        }

        // Validate username (ensure it's not null or empty)
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        // Validate email (basic validation for null and regex for correct email format)
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Validate password (ensure it's not null or empty)
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Trim and standardize username and email
        this.username = username.trim().toLowerCase();
        this.email = email.trim();

        // Store the userId and password (optional trimming if needed)
        this.userId = userId;
        this.password = password;

        // Log success creation of the object
        System.out.println("UserDTO created successfully for username: " + this.username);
    }
}
