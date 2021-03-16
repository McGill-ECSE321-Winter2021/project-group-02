package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.model.Assistant;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Technician;
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
    private CustomerRepository customerRepository;

    @PostMapping (value = {"/customer", "/customer/"})
    public ResponseEntity<Boolean> loginCustomer(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {

        String hashedPassword = Helper.hash(password);

        Customer customer = customerRepository.findByEmail(email);

        if(customer == null || !customer.getPassword().equals(hashedPassword)) return new ResponseEntity<Boolean>(false, HttpStatus.OK);

        String id = ((Integer)customer.getScrsUserId()).toString();
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping (value = {"/assistant", "/assistant/"})
    public ResponseEntity<Boolean> loginAssistant(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {

        String hashedPassword = Helper.hash(password);

        Customer customer = customerRepository.findByEmail(email);

        System.out.println(customer.getPassword());

        if(customer == null || !customer.getPassword().equals(hashedPassword)) return new ResponseEntity<Boolean>(false, HttpStatus.OK);

        String id = ((Integer)customer.getScrsUserId()).toString();
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping (value = {"/technician", "/technician/"})
    public ResponseEntity<Boolean> loginTechnician(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {

        String hashedPassword = Helper.hash(password);

        Customer customer = customerRepository.findByEmail(email);

        System.out.println(customer.getPassword());

        if(customer == null || !customer.getPassword().equals(hashedPassword)) return new ResponseEntity<Boolean>(false, HttpStatus.OK);

        String id = ((Integer)customer.getScrsUserId()).toString();
        Cookie cookie = new Cookie("id", id);
        response.addCookie(cookie);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping (value = {"/logout", "/logout/"})
    public ResponseEntity<Boolean> logout(HttpServletResponse response) {

        String id = "-1";
        Cookie cookie = new Cookie("id", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
