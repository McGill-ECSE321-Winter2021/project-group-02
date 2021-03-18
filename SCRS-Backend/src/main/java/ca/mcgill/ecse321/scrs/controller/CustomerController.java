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
            // Invalid customer. Please submit a valid customer account to be created.
        }
        if (customerService.getCustomerByEmail(customer.getEmail()) != null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.ALREADY_REPORTED);
            // Email already in use, please try a different email address.
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
            // TODO handle no login error with cookies (uncomment next line)
            //return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.UNAUTHORIZED);
            // Please login to modify a customer account.
        }
        if (customer == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.EXPECTATION_FAILED);
            // Invalid customer.
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != customer.getScrsUserId()) //does not have permission to edit.
        {
            // TODO handle bad login error with cookies (uncomment next line)
            //return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.UNAUTHORIZED);
            // You cannot modify a customer account other than your own.
        }
        if (customerService.getCustomerByID(customer.getScrsUserId()) == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.NOT_ACCEPTABLE);
            // No such customer found.
        }
        Customer updatedCustomer = customerService.updateCustomerInfo(customer);
        return new ResponseEntity<>(convertToDto(updatedCustomer), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable String id, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int customerID = Integer.parseInt(id);
        int idCookie = Integer.parseInt(ID);
        if (idCookie == -1)
        {
            // TODO handle no login error with cookies (uncomment next line)
            //return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.UNAUTHORIZED);
            // Please login to delete a customer account.
        }
        Customer customer = customerService.getCustomerByID(customerID);
        if (customer == null)
        {
            return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.NOT_ACCEPTABLE);
            // Invalid customer. Please submit a valid customer account to be deleted.
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(idCookie)) && idCookie != customerID) //does not have permission to edit.
        {
            // TODO handle bad login error with cookies (uncomment next line)
            //return new ResponseEntity<CustomerDto>(new CustomerDto(), HttpStatus.UNAUTHORIZED);
            // You cannot delete a customer account other than your own.
        }
        return new ResponseEntity<>(convertToDto(customerService.deleteCustomer(customer)), HttpStatus.OK);
    }
}
