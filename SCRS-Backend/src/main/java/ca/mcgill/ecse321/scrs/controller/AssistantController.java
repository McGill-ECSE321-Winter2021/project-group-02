package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.AssistantDto;
import ca.mcgill.ecse321.scrs.model.Assistant;
import ca.mcgill.ecse321.scrs.service.AssistantService;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

@RestController
@RequestMapping(path = "/api/assistant")
public class AssistantController
{
    @Autowired
    AssistantService assistantService;
    @Autowired
    SCRSUserService scrsUserService;

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
        return new ResponseEntity<AssistantDto>(convertToDTO(assistantService.createAssistant(assistant.getEmail(), assistant.getName(), hash(assistant.getPassword()), assistant.getPhone())), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<AssistantDto> updateAssistant(@RequestBody Assistant assistant, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to modify an assistant account.");
        }
        if (assistant == null)
        {
            throw new IllegalArgumentException("Invalid assistant");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You do not have permission to edit an admin account.");
        }
        if (assistantService.getAssistantByID(assistant.getScrsUserId()) == null)
        {
            throw new IllegalArgumentException("No such assistant found.");
        }
        return new ResponseEntity<AssistantDto>(convertToDTO(assistantService.updateAssistantInfo(assistant)), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<AssistantDto> deleteAssistant(@RequestParam(value = "id") int assistantID, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to delete an assistant account.");
        }
        Assistant assistant = assistantService.getAssistantByID(assistantID);
        if (assistant == null)
        {
            throw new IllegalArgumentException("Invalid assistant. Please submit a valid assistant account to be deleted.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You do not have permission to edit this account.");
        }
        return new ResponseEntity<AssistantDto>(convertToDTO(assistantService.deleteAssistant(assistant)), HttpStatus.OK);
    }
}
