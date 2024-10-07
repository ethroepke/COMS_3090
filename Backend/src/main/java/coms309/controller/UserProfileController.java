package coms309.controller;

import coms309.entity.UserProfile;
import coms309.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {

    @Autowired
    private final UserProfileRepository userProfileRepository;

    public UserProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping("/all")
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(Math.toIntExact(id));
        return userProfile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public UserProfile createUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfileDetails) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(Math.toIntExact(id));
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            userProfile.setUserName(userProfileDetails.getUserName());
            userProfile.setPassword(userProfileDetails.getPassword());
            userProfile.setUserType(userProfileDetails.getUserType());
            userProfile.setContactInformation(userProfileDetails.getContactInformation());
            userProfile.setJobTitle(userProfileDetails.getJobTitle());
            userProfile.setDepartment(userProfileDetails.getDepartment());
            userProfile.setDateOfHire(userProfileDetails.getDateOfHire());


            UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
            return ResponseEntity.ok(updatedUserProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        if (userProfileRepository.existsById(Math.toIntExact(id))) {
            userProfileRepository.deleteById(Math.toIntExact(id));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
