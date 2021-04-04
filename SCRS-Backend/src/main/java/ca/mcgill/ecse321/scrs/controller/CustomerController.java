package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.service.CustomerService;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDto;
import static ca.mcgill.ecse321.scrs.controller.Helper.hash;

@RestController
@RequestMapping(path = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController
{
    @Autowired
    CustomerService customerService;
    @Autowired
    SCRSUserService scrsUserService;

    @PostMapping(value = {"/create", "/create/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody Customer customer)
    {
        if (customer == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.EXPECTATION_FAILED);
            // Invalid customer. Please submit a valid customer account to be created.
        }
        if (scrsUserService.getSCRSUserByEmail(customer.getEmail()) != null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.ALREADY_REPORTED);
            // Email already in use, please try a different email address.
        }
        Customer newCustomer = customerService.createCustomer(customer.getEmail(), customer.getName(), hash(customer.getPassword()), customer.getPhone());
        return new ResponseEntity<>(convertToDto(newCustomer), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody Customer customer)
    {
        if (customer == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.EXPECTATION_FAILED);
            // Invalid customer.
        }
        if (customerService.getCustomerByID(customer.getScrsUserId()) == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.NOT_ACCEPTABLE);
            // No such customer found.
        }
        if (scrsUserService.getSCRSUserByEmail(customer.getEmail()) != null && scrsUserService.getSCRSUserByEmail(customer.getEmail()).getScrsUserId() != customer.getScrsUserId())
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.ALREADY_REPORTED);
            // Email already in use, please try a different email address.
        }
        Customer updatedCustomer = customerService.updateCustomerInfo(customer);
        return new ResponseEntity<>(convertToDto(updatedCustomer), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable String id)
    {
        int customerID = Integer.parseInt(id);
        Customer customer = customerService.getCustomerByID(customerID);
        if (customer == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.NOT_ACCEPTABLE);
            // Invalid customer. Please submit a valid customer account to be deleted.
        }
        return new ResponseEntity<>(convertToDto(customerService.deleteCustomer(customer)), HttpStatus.OK);
    }

    @GetMapping(value = {"/getByID/{id}", "/getByID/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<CustomerDto> getByID(@PathVariable String id)
    {
        if (id == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        int ID = Integer.parseInt(id);
        Customer customer = customerService.getCustomerByID(ID);

        if (customer == null) return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);
    }

    @GetMapping(value = {"/getByEmail/{email}", "/getByEmail/{email}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<CustomerDto> getByEmail(@PathVariable String email)
    {
        if (email == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        Customer customer = customerService.getCustomerByEmail(email);

        if (customer == null) return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);
    }
}
