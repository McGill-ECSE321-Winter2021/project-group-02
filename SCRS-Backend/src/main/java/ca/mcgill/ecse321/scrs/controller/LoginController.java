package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.model.Assistant;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.SCRSUser;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.service.AssistantService;
import ca.mcgill.ecse321.scrs.service.CustomerService;
import ca.mcgill.ecse321.scrs.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController
{

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AssistantService assistantService;
    @Autowired
    private TechnicianService technicianService;

    @PostMapping (value = {"/customer", "/customer/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> loginCustomer(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {

        String hashedPassword = Helper.hash(password);

        Customer customer = customerService.getCustomerByEmail(email);

        if(customer == null || !customer.getPassword().equals(hashedPassword))
        {
            return new ResponseEntity<>(-1, HttpStatus.OK);
        }

        String id = ((Integer)customer.getScrsUserId()).toString();
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<>(customer.getScrsUserId(), HttpStatus.OK);
    }

    @PostMapping (value = {"/assistant", "/assistant/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> loginAssistant(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {

        String hashedPassword = Helper.hash(password);

        Assistant assistant = assistantService.getAssistantByEmail(email);

        if(assistant == null || !assistant.getPassword().equals(hashedPassword))
        {
            return new ResponseEntity<>(-1, HttpStatus.OK);
        }

        String id = ((Integer)assistant.getScrsUserId()).toString();
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<>(assistant.getScrsUserId(), HttpStatus.OK);
    }

    @PostMapping (value = {"/technician", "/technician/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> loginTechnician(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {

        String hashedPassword = Helper.hash(password);

        Technician technician = technicianService.getTechnicianByEmail(email);

        if(technician == null || !technician.getPassword().equals(hashedPassword))
        {
            return new ResponseEntity<>(-1, HttpStatus.OK);
        }

        String id = ((Integer)technician.getScrsUserId()).toString();
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<>(technician.getScrsUserId(), HttpStatus.OK);
    }

    @GetMapping(value = {"/logout", "/logout/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<Boolean> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("id", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = {"/type/{id}", "/type/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> getType(@PathVariable String id) {
        if(id == null)return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        int ID = Integer.parseInt(id);
        Customer customer = customerService.getCustomerByID(ID);
        Technician technician = technicianService.getTechnicianByID(ID);
        Assistant assistant = assistantService.getAssistantByID(ID);
        if(assistant != null) return new ResponseEntity<>("assistant", HttpStatus.OK);
        else if(technician != null) return new ResponseEntity<>("technician", HttpStatus.OK);
        else if(customer != null) return new ResponseEntity<>("customer", HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
