package onetoone.Laptops;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Persons.Person;
import onetoone.Persons.PersonRepository;

/**
 *
 * @author Vivek Bengre
 *
 */

@RestController
public class LaptopController {

    @Autowired
    LaptopRepository laptopRepository;

    @Autowired
    PersonRepository personRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/laptops")
    List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }

    @GetMapping(path = "/laptops/{id}")
    Laptop getLaptopById(@PathVariable int id) {
        Optional<Laptop> laptop = laptopRepository.findById(id);
        return laptop.orElse(null);  // Return null if not found (consider adding a proper error handling strategy)
    }

    @PostMapping(path = "/laptops")
    String createLaptop(@RequestBody Laptop laptop) {
        if (laptop == null)
            return failure;
        laptopRepository.save(laptop);
        return success;
    }

    @PutMapping(path = "/laptops/{id}")
    Laptop updateLaptop(@PathVariable int id, @RequestBody Laptop request) {
        Optional<Laptop> existingLaptop = laptopRepository.findById(id);
        if (!existingLaptop.isPresent())
            return null;

        // Save the updated laptop
        laptopRepository.save(request);
        return request;
    }

    @Transactional
    @DeleteMapping(path = "/laptops/{id}")
    String deleteLaptop(@PathVariable int id) {
        // Check if there is a Person associated with the laptop
        Person person = personRepository.findByLaptop_Id(id);

        if (person != null) {
            // Remove the laptop reference in Person entity
            person.setLaptop(null);
            personRepository.save(person);
        }

        // Delete the laptop
        Optional<Laptop> laptop = laptopRepository.findById(id);
        if (laptop.isPresent()) {
            laptopRepository.deleteById(id);
            return success;
        }
        return failure;
    }
}