
package coms309.controller;

import coms309.entity.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import coms309.service.UserService;
import coms309.exception.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/timeWorked")
public class TimeWorkedController {

    private static final Logger logger = LoggerFactory.getLogger(TimeWorkedController.class);

    @Autowired

    private UserService userService;

    // Get user by ID and return timeWorked along with other details
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getTimeWorkedById(@PathVariable Long id) {
        logger.info("Fetching user with id: {}", id);
        UserProfile user = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateTimeWorked(@PathVariable Long id, @Valid @RequestBody UserProfile userDetails) {
        logger.info("Updating timeWorked for user with id: {}", id);
        UserProfile updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // Submit user's time for the week
    @PostMapping("/timeweek")
    public ResponseEntity<String> submitTimeForWeek(@Valid @RequestBody UserProfile user) {
        logger.info("Submitting time for week for user with id: {}", user.getUserId());
        if (user.getTimeWorked() < 0 || user.getTimeWorked() > 168) { // A week has 168 hours max
            return ResponseEntity.badRequest().body("Invalid time entry. Time should be between 0 and 168 hours.");
        }
        boolean result = userService.submitTimeForWeek(user);
        if (!result) {
            return ResponseEntity.badRequest().body("Failed to submit time for the week.");
        }
        return ResponseEntity.ok("Time for the week submitted successfully.");
    }

    // Unsubmit user's time for the week if they made a mistake
    @DeleteMapping("/timeweek")
    public ResponseEntity<String> unsubmitTimeForWeek(@Valid @RequestBody UserProfile user) {
        logger.info("Unsubmitting time for week for user with id: {}", user.getUserId());
        boolean result;
        if (userService.unsubmitTimeForWeek(user)) result = true;
        else result = false;
        if (!result) {
            return ResponseEntity.badRequest().body("Failed to unsubmit time for the week.");
        }
        return ResponseEntity.ok("Time for the week unsubmited successfully.");
    }
}
