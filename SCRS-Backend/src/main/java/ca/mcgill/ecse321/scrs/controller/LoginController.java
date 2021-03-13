package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.model.Assistant;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Technician;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController
{
    //routes

    @PostMapping ("/login/customer")
    public ResponseEntity<Boolean> loginCustomer(@RequestBody Customer user) {
        String hashedPassword = Helper.hash(user.getPassword());
        System.out.println(user.getName() + ", " + hashedPassword);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping ("/login/assistant")
    public ResponseEntity<Boolean> loginAssistant(@RequestBody Assistant user) {
        String hashedPassword = Helper.hash(user.getPassword());
        System.out.println(user.getName() + ", " + hashedPassword);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping ("/login/technician")
    public ResponseEntity<Boolean> loginTechnician(@RequestBody Technician user) {
        String hashedPassword = Helper.hash(user.getPassword());
        System.out.println(user.getName() + ", " + hashedPassword);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
