
package onetoone.Persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import onetoone.Laptops.Laptop;
import onetoone.Laptops.LaptopRepository;

import java.util.List;

/**
 * REST Controller for managing Person entities
 */
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LaptopRepository laptopRepository;

    private final String success = "Operation was successful";
    private final String failure = "Operation failed";

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/persons/{id}")
    public Person getPersonById(@PathVariable int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
    }

    @PostMapping("/persons")
    public String createPerson(@RequestBody Person person) {
        if (person == null) {
            return failure;
        }
        personRepository.save(person);
        return success;
    }

    @PutMapping("/persons/{id}")
    public String updatePerson(@PathVariable int id, @RequestBody Person person) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        
        if (existingPerson.getId() != id) {
            throw new RuntimeException("Person ID mismatch");
        }
        
        personRepository.save(person);
        return success;
    }

    @PutMapping("/persons/{personId}/laptops/{laptopId}")
    public String assignLaptopToPerson(@PathVariable int personId, @PathVariable int laptopId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + personId));
        Laptop laptop = laptopRepository.findById(laptopId)
                .orElseThrow(() -> new RuntimeException("Laptop not found with id: " + laptopId));

        laptop.setPerson(person);
        person.setLaptop(laptop);
        personRepository.save(person);
        return success;
    }

    @DeleteMapping("/persons/{id}")
    public String deletePerson(@PathVariable int id) {
        personRepository.deleteById(id);
        return success;
    }
}
