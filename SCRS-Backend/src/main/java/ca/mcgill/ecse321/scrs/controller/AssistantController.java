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
    @CrossOrigin(origins = "*")
    public ResponseEntity<AssistantDto> createAssistant(@RequestBody Assistant assistant)
    {
        if (assistant == null)
        {
            return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.EXPECTATION_FAILED);
            // Invalid assistant. Please submit a valid assistant account to be created.
        }
        if (scrsUserService.getSCRSUserByEmail(assistant.getEmail()) != null)
        {
            return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.ALREADY_REPORTED);
            // Email already in use, please try a different email address.
        }
        Assistant newAssistant = assistantService.createAssistant(assistant.getEmail(), assistant.getName(), hash(assistant.getPassword()), assistant.getPhone());
        return new ResponseEntity<>(convertToDto(newAssistant), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<AssistantDto> updateAssistant(@RequestBody Assistant assistant, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            // TODO handle no login error with cookies (uncomment next line)
            //return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.UNAUTHORIZED);
            // Please login to modify an assistant account.
        }
        if (assistant == null)
        {
            return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.EXPECTATION_FAILED);
            // Invalid assistant
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            // TODO handle bad login error with cookies (uncomment next line)
            //return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.UNAUTHORIZED);
            // You do not have permission to edit an admin account.
        }
        if (assistantService.getAssistantByID(assistant.getScrsUserId()) == null)
        {
            return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.NOT_ACCEPTABLE);
            // No such assistant found.
        }
        if (scrsUserService.getSCRSUserByEmail(assistant.getEmail()) != null)
        {
            return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.ALREADY_REPORTED);
            // Email already in use, please try a different email address.
        }
        Assistant updatedAssistant = assistantService.updateAssistantInfo(assistant);
        return new ResponseEntity<>(convertToDto(updatedAssistant), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<AssistantDto> deleteAssistant(@PathVariable String id, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int assistantID = Integer.parseInt(id);
        int idCookie = Integer.parseInt(ID);
        if (idCookie == -1)
        {
            // TODO handle no login error with cookies (uncomment next line)
            //return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.UNAUTHORIZED);
            //Please login to delete an assistant account.
        }
        Assistant assistant = assistantService.getAssistantByID(assistantID);
        if (assistant == null)
        {
            return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.NOT_ACCEPTABLE);
            // Invalid assistant. Please submit a valid assistant account to be deleted.
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(idCookie))) //does not have permission to edit.
        {
            // TODO handle bad login error with cookies (uncomment next line)
            //return new ResponseEntity<AssistantDto>(new AssistantDto(), HttpStatus.UNAUTHORIZED);
            // You do not have permission to edit this account.
        }
        return new ResponseEntity<>(convertToDto(assistantService.deleteAssistant(assistant)), HttpStatus.OK);
    }

    @GetMapping(value = {"/getByID/{id}", "/getByID/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<AssistantDto> getByID(@PathVariable String id)
    {
        if (id == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        int ID = Integer.parseInt(id);
        Assistant assistant = assistantService.getAssistantByID(ID);

        if (assistant == null) return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(convertToDto(assistant), HttpStatus.OK);
    }

    @GetMapping(value = {"/getByEmail/{email}", "/getByEmail/{email}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<AssistantDto> getByEmail(@PathVariable String email)
    {
        if (email == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        Assistant assistant = assistantService.getAssistantByEmail(email);

        if (assistant == null) return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(convertToDto(assistant), HttpStatus.OK);
    }
}
