
package com.coms309.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import coms309.entity.User;
import coms309.service.UserService;
import com.coms309.exception.ResourceNotFoundException;

import javax.validation.Valid;

@RestController
@RequestMapping("/timeWorked")
public class TimeWorkedController {

    private static final Logger logger = LoggerFactory.getLogger(TimeWorkedController.class);

    @Autowired
    private UserService userService;

    // Get user by ID and return timeWorked along with other details
    @GetMapping("/{id}")
    public ResponseEntity<User> getTimeWorkedById(@PathVariable Long id) {
        logger.info("Fetching user with id: {}", id);
        User user = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateTimeWorked(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        logger.info("Updating timeWorked for user with id: {}", id);
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // Submit user's time for the week
    @PostMapping("/timeweek")
    public ResponseEntity<String> submitTimeForWeek(@Valid @RequestBody User user) {
        logger.info("Submitting time for week for user with id: {}", user.getId());
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
    public ResponseEntity<String> unsubmitTimeForWeek(@Valid @RequestBody User user) {
        logger.info("Unsubmitting time for week for user with id: {}", user.getId());
        boolean result = userService.unsubmitTimeForWeek(user);
        if (!result) {
            return ResponseEntity.badRequest().body("Failed to unsubmit time for the week.");
        }
        return ResponseEntity.ok("Time for the week unsubmited successfully.");
    }
}
