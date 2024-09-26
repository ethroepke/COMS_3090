package onetoone.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Laptops.Laptop;
import onetoone.Laptops.LaptopRepository;

/**
 *
 * @author Vivek Bengre
 *
 */

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LaptopRepository laptopRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);  // Return null if not found
    }

    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @PutMapping("/users/{id}")
    String updateUser(@PathVariable Long id, @RequestBody User request) {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            return failure;  // Return failure if the user is not found
        }
        userRepository.save(request);
        return success;  // Return success after saving the updated user
    }

    @PutMapping("/users/{userId}/laptops/{laptopId}")
    String assignLaptopToUser(@PathVariable Long userId, @PathVariable Long laptopId){
        Optional<User> user = userRepository.findById(userId);
        Optional<Laptop> laptop = laptopRepository.findById(laptopId);

        if (!user.isPresent() || !laptop.isPresent())
            return failure;  // Return failure if either user or laptop is not found

        laptop.get().setUser(user.get());
        user.get().setLaptop(laptop.get());

        userRepository.save(user.get());
        return success;
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return failure;  // Return failure if user doesn't exist
        }
        userRepository.deleteById(id);
        return success;
    }
}