package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }
    @GetMapping("/1")
    public String welcome1() {
        return "This is the helloWord file";
    }

    @GetMapping("/demo1")
    public String introduce(){return "ThienNguyen_experiment1s"}


    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello My name is : " + name;
    }
}
