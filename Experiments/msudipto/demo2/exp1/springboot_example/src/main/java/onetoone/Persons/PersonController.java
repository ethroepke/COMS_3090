package onetoone.Persons;

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
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LaptopRepository laptopRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/Persons")
    List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping(path = "/Persons/{id}")
    Person getPersonById(@PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);  // Handle Optional safely
    }

    @PostMapping(path = "/Persons")
    String createPerson(@RequestBody Person person) {
        if (person == null)
            return failure;
        personRepository.save(person);
        return success;
    }

    @PutMapping("/Persons/{id}")
    Person updatePerson(@PathVariable Long id, @RequestBody Person request) {
        Optional<Person> existingPerson = personRepository.findById(id);

        if (!existingPerson.isPresent()) {
            throw new RuntimeException("Person id does not exist");
        } else if (request.getId() != id) {
            throw new RuntimeException("Path variable id does not match Person request id");
        }

        // Save the updated person
        personRepository.save(request);
        return request;
    }

    @PutMapping("/Persons/{PersonId}/laptops/{laptopId}")
    String assignLaptopToPerson(@PathVariable Long PersonId, @PathVariable Long laptopId) {
        Optional<Person> person = personRepository.findById(PersonId);
        Optional<Laptop> laptop = laptopRepository.findById(laptopId);

        if (!person.isPresent() || !laptop.isPresent())
            return failure;

        // Set relationships both ways
        laptop.get().setPerson(person.get());
        person.get().setLaptop(laptop.get());

        personRepository.save(person.get());
        return success;
    }

    @DeleteMapping(path = "/Persons/{id}")
    String deletePerson(@PathVariable Long id) {
        if (!personRepository.existsById(id)) {
            return failure;
        }
        personRepository.deleteById(id);
        return success;
    }
}