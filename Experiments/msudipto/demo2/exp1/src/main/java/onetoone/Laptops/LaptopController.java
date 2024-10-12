
package onetoone.Laptops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Laptop entities
 */
@RestController
public class LaptopController {

    @Autowired
    LaptopRepository laptopRepository;

    private final String success = "Operation was successful";
    private final String failure = "Operation failed";

    @GetMapping("/laptops")
    public List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }

    @GetMapping("/laptops/{id}")
    public Laptop getLaptopById(@PathVariable int id) {
        return laptopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laptop not found with id: " + id));
    }

    @PostMapping("/laptops")
    public String createLaptop(@RequestBody Laptop laptop) {
        if (laptop == null) {
            return failure;
        }
        laptopRepository.save(laptop);
        return success;
    }

    @PutMapping("/laptops/{id}")
    public String updateLaptop(@PathVariable int id, @RequestBody Laptop laptop) {
        Laptop existingLaptop = laptopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laptop not found with id: " + id));
        
        if (existingLaptop.getId() != id) {
            throw new RuntimeException("Laptop ID mismatch");
        }
        
        laptopRepository.save(laptop);
        return success;
    }

    @DeleteMapping("/laptops/{id}")
    public String deleteLaptop(@PathVariable int id) {
        laptopRepository.deleteById(id);
        return success;
    }
}
