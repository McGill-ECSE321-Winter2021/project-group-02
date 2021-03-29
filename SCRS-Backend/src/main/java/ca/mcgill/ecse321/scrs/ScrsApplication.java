package ca.mcgill.ecse321.scrs;

import ca.mcgill.ecse321.scrs.controller.Helper;
import ca.mcgill.ecse321.scrs.dao.AssistantRepository;
import ca.mcgill.ecse321.scrs.model.Assistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@CrossOrigin(origins = "*")
@RestController
@SpringBootApplication
public class ScrsApplication
{
    static AssistantRepository staticAssistantRepository;

    @Autowired
    AssistantRepository assistantRepository;

    @PostConstruct
    public void init() {
        staticAssistantRepository = assistantRepository;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(ScrsApplication.class, args);

        if (staticAssistantRepository.count() == 0)
        {
            String hashedDefaultAdminPass = Helper.hash("password");
            Assistant assistant = new Assistant("admin", hashedDefaultAdminPass, "admin@scrs.ca", "111111111", null);
            staticAssistantRepository.save(assistant);
        }
    }

}