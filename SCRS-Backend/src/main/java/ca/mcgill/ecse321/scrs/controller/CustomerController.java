package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDTO;
import static ca.mcgill.ecse321.scrs.controller.Helper.hash;

@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("Invalid customer. Please submit a valid customer account to be created.");
        }
        if (customerService.getCustomerByEmail(customer.getEmail()) != null)
        {
            throw new IllegalArgumentException("Email already in use, please try a different email address.");
        }
        return new ResponseEntity<CustomerDto>(convertToDTO(customerService.createCustomer(customer.getEmail(), customer.getName(), hash(customer.getPassword()), customer.getPhone())), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("Invalid customer.");
        }
        if (customerService.getCustomerByID(customer.getScrsUserId()) == null)
        {
            throw new IllegalArgumentException("No such customer found.");
        }
        return new ResponseEntity<CustomerDto>(convertToDTO(customerService.updateCustomerInfo(customer)), HttpStatus.OK);
    }

}
