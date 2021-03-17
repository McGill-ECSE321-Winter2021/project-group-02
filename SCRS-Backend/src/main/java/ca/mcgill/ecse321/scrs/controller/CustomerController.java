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

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

@RestController
@RequestMapping(path = "/api/customer",produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController
{
    @Autowired
    CustomerService customerService;
    @Autowired
    SCRSUserService scrsUserService;

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody Customer customer)
    {
        if (customer == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.EXPECTATION_FAILED);
            //throw new IllegalArgumentException("Invalid customer. Please submit a valid customer account to be created.");
        }
        if (customerService.getCustomerByEmail(customer.getEmail()) != null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.ALREADY_REPORTED);
            //throw new IllegalArgumentException("Email already in use, please try a different email address.");
        }
        Customer newCustomer = customerService.createCustomer(customer.getEmail(), customer.getName(), hash(customer.getPassword()), customer.getPhone());
        return new ResponseEntity<>(convertToDto(newCustomer), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody Customer customer, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            //throw new IllegalArgumentException("Please login to modify a customer account.");
        }
        if (customer == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.EXPECTATION_FAILED);
            //throw new IllegalArgumentException("Invalid customer.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != customer.getScrsUserId()) //does not have permission to edit.
        {
            //throw new IllegalArgumentException("You cannot modify a customer account other than your own.");
        }
        if (customerService.getCustomerByID(customer.getScrsUserId()) == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.NOT_ACCEPTABLE);
            //throw new IllegalArgumentException("No such customer found.");
        }
        Customer updatedCustomer = customerService.updateCustomerInfo(customer);
        return new ResponseEntity<>(convertToDto(updatedCustomer), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<CustomerDto> deleteCustomer(@RequestParam(value = "id") String customerIDString, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int customerID = Integer.parseInt(customerIDString);
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            //throw new IllegalArgumentException("Please login to delete a customer account.");
        }
        Customer customer = customerService.getCustomerByID(customerID);
        if (customer == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.NOT_ACCEPTABLE);
            //throw new IllegalArgumentException("Invalid customer. Please submit a valid customer account to be deleted.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != customerID) //does not have permission to edit.
        {
            // throw new IllegalArgumentException("You cannot delete a customer account other than your own.");
        }
        return new ResponseEntity<>(convertToDto(customerService.deleteCustomer(customer)), HttpStatus.OK);
    }
}
