package coms309.controller;

import coms309.dto.SalaryDTO;
import coms309.entity.Salary;
import coms309.entity.UserProfile;
import coms309.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/api/paydetails")
public class PayDetailController {

    @Autowired
    private SalaryRepository salaryRepository;

    // Create PayDetails
    @PostMapping("/create")
    public ResponseEntity<Salary> createPayDetails(@Valid @RequestBody SalaryDTO salaryDTO) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(salaryDTO.getUsername());

        Salary payDetails = new Salary(userProfile, salaryDTO.getHoursWorked(), salaryDTO.getPayRate(), salaryDTO.getBonusPay(), salaryDTO.getDeductibles());
        // Assume you have a method to get the UserProfile based on some ID
//         payDetails.setUserProfile(getUserProfileById(someUserProfileId));

        Salary savedPayDetails = salaryRepository.save(payDetails);
        return ResponseEntity.status(201).body(savedPayDetails);
    }
    @GetMapping("/search/{userName}")
    public ResponseEntity<Salary> getPayDetailsByUserName(@PathVariable UserProfile userName) {
        Optional<Salary> payDetails = salaryRepository.findByUserProfile(userName);
        return payDetails.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update PayDetails
    @PutMapping("/{payUserName}")
    public ResponseEntity<Salary> updatePayDetails(@PathVariable UserProfile user, @Valid @RequestBody SalaryDTO salaryDTO) {
        Optional<Salary> optionalPayDetails = salaryRepository.findByUserProfile(user);

        if (optionalPayDetails.isPresent()) {
            Salary payDetails = optionalPayDetails.get();
            payDetails.setHoursWorked(salaryDTO.getHoursWorked());
            payDetails.setPayRate(salaryDTO.getPayRate());
            payDetails.setBonusPay(salaryDTO.getBonusPay());
            payDetails.setDeductibles(salaryDTO.getDeductibles());

            // Recalculate gross and take-home pay if needed
            payDetails.setGrossPay(payDetails.calculateGrossPay());
            payDetails.setTakeHomePay(payDetails.calculateTakeHomePay());

            Salary updatedPayDetails = salaryRepository.save(payDetails);
            return ResponseEntity.ok(updatedPayDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/sendRequest")
    public ResponseEntity<Salary> sendRequest(@RequestBody UserProfile user ){
            return null;

    }
}
