package onetoone.Laptops;

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

/**
 *
 * @author Vivek Bengre
 *
 */

@RestController
public class LaptopController {

    @Autowired
    LaptopRepository laptopRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/laptops")
    List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }

    @GetMapping(path = "/Laptops/{id}")
    Laptop getLaptopById(@PathVariable Long id) {
        Optional<Laptop> laptop = laptopRepository.findById(id);
        return laptop.orElse(null);  // Return null if not found
    }

    @PostMapping(path = "/laptops")
    String createLaptop(@RequestBody Laptop laptop) {
        if (laptop == null) {
            return failure;
        }
        laptopRepository.save(laptop);
        return success;
    }

    @PutMapping(path = "/laptops/{id}")
    String updateLaptop(@PathVariable Long id, @RequestBody Laptop request) {
        Optional<Laptop> existingLaptop = laptopRepository.findById(id);
        if (!existingLaptop.isPresent()) {
            return failure;  // Return failure if the laptop does not exist
        }
        laptopRepository.save(request);
        return success;  // Return success after saving the updated laptop
    }

    @DeleteMapping(path = "/laptops/{id}")
    String deleteLaptop(@PathVariable Long id) {
        if (!laptopRepository.existsById(id)) {
            return failure;  // Return failure if the laptop doesn't exist
        }
        laptopRepository.deleteById(id);
        return success;
    }
}