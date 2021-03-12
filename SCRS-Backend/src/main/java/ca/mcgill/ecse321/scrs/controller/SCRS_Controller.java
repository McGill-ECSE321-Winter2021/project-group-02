package ca.mcgill.ecse321.scrs.controller;
import ca.mcgill.ecse321.scrs.service.AssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SCRS_Controller
{
    //@Autowired
    //private AssistantService assistantService;

    public static void main(String[] args) {
        SpringApplication.run(SCRS_Controller.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
