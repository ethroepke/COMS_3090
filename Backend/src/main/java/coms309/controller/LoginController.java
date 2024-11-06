
package coms309.controller;

import coms309.dto.SignUpDTO;
import coms309.dto.UserDTO;
import coms309.entity.UserProfile;
import coms309.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<UserProfile> existUser = userProfileRepository.findByUsernameAndPassword(username, password);
        if (existUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed. Invalid credentials.");
        }
        return ResponseEntity.ok("Login successful");
    }

    // Sign up: POST name, email, username, password, verify password
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpUser) {
        Optional<UserProfile> existingUser = userProfileRepository.findByUsername(signUpUser.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }
        // Create a new user and save to the database
        UserProfile newUser = new UserProfile();
        newUser.setFullName(signUpUser.getFull_name());
        newUser.setEmail(signUpUser.getEmail());
        newUser.setUsername(signUpUser.getUsername());
        newUser.setPassword(signUpUser.getPassword());
        userProfileRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User signed up successfully.");
    }

    // Forgot Password: GET email to check if in database
    @GetMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        Optional<UserProfile> existUser = userProfileRepository.findByEmail(email);
        if (existUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user exists with this email.");
        }
        return ResponseEntity.ok("User exists.");
    }

    // Reset Password: PUT to update password
    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        Optional<UserProfile> existUser = userProfileRepository.findByEmail(email);
        if (existUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user exists with this email.");
        }
        UserProfile user = existUser.get();
        user.setPassword(newPassword);
        userProfileRepository.save(user);
        return ResponseEntity.ok("Password successfully updated.");
    }

    // Paycheck Search: GET username to verify in database
    @GetMapping("/paycheckSearch")
    public ResponseEntity<String> paycheckSearch(@RequestParam String username) {
        Optional<UserProfile> existUser = userProfileRepository.findByUsername(username);
        if (existUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.ok("User found.");
    }

    // Paycheck Overview Page: GET details of take home pay, gross pay, hours worked, pay rate, bonus, deductibles
    @GetMapping("/paycheckOverview")
    public ResponseEntity<String> paycheckOverview(@RequestParam String username) {
        Optional<UserProfile> existUser = userProfileRepository.findByUsername(username);
        if (existUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        // For demonstration, return static paycheck details
        return ResponseEntity.ok("Take home pay: $2000, Gross pay: $2500, Hours worked: 160, Pay rate: $15, Bonus: $200, Deductibles: $300");
    }

    // Paycheck Modify Page: PUT to modify hours worked, pay rate, bonus, and deductibles
    @PutMapping("/paycheckModify")
    public ResponseEntity<String> paycheckModify(@RequestParam String username,
                                                 @RequestParam int hoursWorked,
                                                 @RequestParam double payRate,
                                                 @RequestParam double bonus,
                                                 @RequestParam double deductibles) {
        Optional<UserProfile> existUser = userProfileRepository.findByUsername(username);
        if (existUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        // For demonstration, update static paycheck details (this would normally involve updating the user's paycheck details)
        return ResponseEntity.ok("Paycheck updated successfully: Hours worked: " + hoursWorked +
                ", Pay rate: " + payRate + ", Bonus: " + bonus + ", Deductibles: " + deductibles);
    }
}
