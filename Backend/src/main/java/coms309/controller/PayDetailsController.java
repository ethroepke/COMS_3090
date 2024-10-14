package coms309.controller;

import coms309.dto.PayDetailsDTO;
import coms309.entity.PayDetails;
import coms309.repository.PayDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/api/paydetails")
public class PayDetailsController {

    @Autowired
    private PayDetailRepository payDetailRepository;

    // Create PayDetails
    @PostMapping
    public ResponseEntity<PayDetails> createPayDetails(@Valid @RequestBody PayDetailsDTO payDetailsDTO) {
        PayDetails payDetails = new PayDetails();
        payDetails.setHoursWorked(payDetailsDTO.getHoursWorked());
        payDetails.setPayRate(payDetailsDTO.getPayRate());
        payDetails.setBonusPay(payDetailsDTO.getBonusPay());
        payDetails.setDeductibles(payDetailsDTO.getDeductibles());

        // Assume you have a method to get the UserProfile based on some ID
        // payDetails.setUserProfile(getUserProfileById(someUserProfileId));

        PayDetails savedPayDetails = payDetailRepository.save(payDetails);
        return ResponseEntity.status(201).body(savedPayDetails);
    }

    // Get PayDetails by userProfileId
    @GetMapping("/{userProfileId}")
    public ResponseEntity<PayDetails> getPayDetails(@PathVariable Long userProfileId) {
        Optional<PayDetails> payDetails = payDetailRepository.findByUserProfileId(userProfileId);
        return payDetails.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update PayDetails
    @PutMapping("/{payId}")
    public ResponseEntity<PayDetails> updatePayDetails(@PathVariable Long payId, @Valid @RequestBody PayDetailsDTO payDetailsDTO) {
        Optional<PayDetails> optionalPayDetails = payDetailRepository.findById(payId);

        if (optionalPayDetails.isPresent()) {
            PayDetails payDetails = optionalPayDetails.get();
            payDetails.setHoursWorked(payDetailsDTO.getHoursWorked());
            payDetails.setPayRate(payDetailsDTO.getPayRate());
            payDetails.setBonusPay(payDetailsDTO.getBonusPay());
            payDetails.setDeductibles(payDetailsDTO.getDeductibles());
            
            // Recalculate gross and take-home pay if needed
            payDetails.setGrossPay(payDetails.calculateGrossPay());
            payDetails.setTakeHomePay(payDetails.calculateTakeHomePay());

            PayDetails updatedPayDetails = payDetailRepository.save(payDetails);
            return ResponseEntity.ok(updatedPayDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete PayDetails by payId
    @DeleteMapping("/{payId}")
    public ResponseEntity<Void> deletePayDetails(@PathVariable Long payId) {
        if (payDetailRepository.existsById(payId)) {
            payDetailRepository.deleteById(payId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
