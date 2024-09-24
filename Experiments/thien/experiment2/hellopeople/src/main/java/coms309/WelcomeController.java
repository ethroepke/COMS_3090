package coms309;

import coms309.people.Person;
import coms309.people.Students;
import org.springframework.web.bind.annotation.*;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {
    @GetMapping("/")
    public String welcome() {return "Hello and welcome to COMS 309";}

//    @GetMapping("/{name}/{id}")
//    public String greetingUser1(@PathVariable String name, @PathVariable int id) {
//        return "Hello My name is : " + name + " My university id is: " + id;
//    }

    @GetMapping("/experiment1")
    public String welcome1() {return "This is hello world file";}

}
