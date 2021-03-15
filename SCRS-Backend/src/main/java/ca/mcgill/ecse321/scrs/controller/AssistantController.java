package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.AssistantDto;
import ca.mcgill.ecse321.scrs.model.Assistant;
import ca.mcgill.ecse321.scrs.service.AssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/assistant")
public class AssistantController
{
    @Autowired
    AssistantService assistantService;

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<AssistantDto> createAssistant(@RequestBody Assistant assistant)
    {
        if (assistant == null)
        {
            throw new IllegalArgumentException("Invalid assistant. Please submit a valid assistant account to be created.");
        }
        if (assistantService.getAssistantByEmail(assistant.getEmail()) != null)
        {
            throw new IllegalArgumentException("Email already in use, please try a different email address.");
        }
        return new ResponseEntity<AssistantDto>(convertToDTO(assistantService.createAssistant(assistant.getEmail(), assistant.getName(), assistant.getPassword(), assistant.getPhone())), HttpStatus.OK);
    }

    // ================= Private Helpers ================

    private AssistantDto convertToDTO(Assistant a)
    {
        if (a == null) throw new IllegalArgumentException("There is no such assistant!");
        return new AssistantDto(a.getScrsUserId(), a.getName(), a.getEmail(), a.getPhone());
    }
}
