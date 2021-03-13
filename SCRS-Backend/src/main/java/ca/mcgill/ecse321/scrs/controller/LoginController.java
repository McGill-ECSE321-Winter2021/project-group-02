package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.model.Assistant;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Technician;
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
    @PostMapping ("/customer")
    public ResponseEntity<Boolean> loginCustomer(@RequestBody Customer user, HttpServletResponse response) {
        String hashedPassword = Helper.hash(user.getPassword());
        System.out.println(user.getName() + ", " + hashedPassword);

        String id = "-1";
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping ("/assistant")
    public ResponseEntity<Boolean> loginAssistant(@RequestBody Assistant user, HttpServletResponse response) {
        String hashedPassword = Helper.hash(user.getPassword());
        System.out.println(user.getName() + ", " + hashedPassword);

        String id = "-1";
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping ("/technician")
    public ResponseEntity<Boolean> loginTechnician(@RequestBody Technician user, HttpServletResponse response) {
        String hashedPassword = Helper.hash(user.getPassword());
        System.out.println(user.getName() + ", " + hashedPassword);

        String id = "-1";
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
