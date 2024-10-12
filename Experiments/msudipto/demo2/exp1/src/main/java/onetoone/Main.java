
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
 * Main class for initializing the Spring Boot application
 * Author: Vivek Bengre
 */

@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create Persons with their laptops and save to the repository
    @Bean
    CommandLineRunner initPerson(PersonRepository personRepository, LaptopRepository laptopRepository) {
        return args -> {
            try {
                Person[] persons = {
                    new Person("John", "john@somemail.com"),
                    new Person("Jane", "jane@somemail.com"),
                    new Person("Justin", "justin@somemail.com")
                };

                Laptop[] laptops = {
                    new Laptop(2.5, 4, 8, "Lenovo", 300),
                    new Laptop(4.1, 8, 16, "Hp", 800),
                    new Laptop(3.5, 32, 32, "Dell", 2300)
                };

                for (int i = 0; i < persons.length; i++) {
                    persons[i].setLaptop(laptops[i]);
                    personRepository.save(persons[i]);
                }
            } catch (Exception e) {
                System.err.println("Error initializing persons and laptops: " + e.getMessage());
            }
        };
    }
}
