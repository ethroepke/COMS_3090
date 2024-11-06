package coms309.controller;

import coms309.entity.Salary;
import coms309.service.PayDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing salary details.
 */
@RestController
@RequestMapping("/api/salary")
public class PayDetailController {

    @Autowired
    private PayDetailService payDetailService;

    /**
     * Get salary details for a user.
     *
     * @param userId the user ID
     * @return the salary details
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getSalaryForUser(@PathVariable Long userId) {
        return payDetailService.getSalaryForUserResponse(userId);
    }

    /**
     * Get all salaries for a user.
     *
     * @param userId the user ID
     * @return all salaries
     */
    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllSalariesForUser(@PathVariable Long userId) {
        return payDetailService.getAllSalariesForUserResponse(userId);
    }

    /**
     * Create or update salary details.
     *
     * @param salary the salary details
     * @return the updated or created salary
     */
    @PostMapping("/update")
    public ResponseEntity<?> createOrUpdateSalary(@RequestBody Salary salary) {
        return payDetailService.createOrUpdateSalaryResponse(salary);
    }
    /**
     * Get salary details for a user by username.
     *
     * @param username the username
     * @return the salary details
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getSalaryByUsername(@PathVariable String username) {
        return payDetailService.getSalaryByUsernameResponse(username);
    }
    /**
     * Delete salary details by ID.
     *
     * @param salaryId the salary ID
     * @return the response indicating success or failure
     */
    @DeleteMapping("/delete/{salaryId}")
    public ResponseEntity<?> deleteSalary(@PathVariable Long salaryId) {
        return payDetailService.deleteSalaryResponse(salaryId);
    }
}
