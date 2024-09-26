package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetoone.Laptops.Laptop;
import onetoone.Laptops.LaptopRepository;
import onetoone.Persons.Person;
import onetoone.Persons.PersonRepository;

/**
 *
 * @author Vivek Bengre
 *
 */

@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * Create 3 Persons with their machines.
     * @param personRepository Repository for the Person entity.
     * @param laptopRepository Repository for the Laptop entity.
     * Creates a CommandLineRunner to enter dummy data into the database.
     * As mentioned in Person.java, associating the Laptop object with the Person will save it into the database because of the CascadeType.
     */
    @Bean
    CommandLineRunner initPerson(PersonRepository personRepository, LaptopRepository laptopRepository) {
        return args -> {
            // Creating Person objects
            Person person1 = new Person("John", "john@somemail.com");
            Person person2 = new Person("Jane", "jane@somemail.com");
            Person person3 = new Person("Justin", "justin@somemail.com");

            // Creating Laptop objects
            Laptop laptop1 = new Laptop(2.5, 4, 8, "Lenovo", 300);
            Laptop laptop2 = new Laptop(4.1, 8, 16, "Hp", 800);
            Laptop laptop3 = new Laptop(3.5, 32, 32, "Dell", 2300);

            // Associating laptops with persons
            if (person1 != null && laptop1 != null) {
                person1.setLaptop(laptop1);
            }
            if (person2 != null && laptop2 != null) {
                person2.setLaptop(laptop2);
            }
            if (person3 != null && laptop3 != null) {
                person3.setLaptop(laptop3);
            }

            // Saving persons (which will cascade save the laptops)
            personRepository.save(person1);
            personRepository.save(person2);
            personRepository.save(person3);
        };
    }
}