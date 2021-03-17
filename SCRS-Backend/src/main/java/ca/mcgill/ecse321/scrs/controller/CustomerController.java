package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.service.CustomerService;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

@RestController
@RequestMapping(path = "/api/customer")
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
            throw new IllegalArgumentException("Invalid customer. Please submit a valid customer account to be created.");
        }
        if (customerService.getCustomerByEmail(customer.getEmail()) != null)
        {
            throw new IllegalArgumentException("Email already in use, please try a different email address.");
        }
        return new ResponseEntity<CustomerDto>(convertToDTO(customerService.createCustomer(customer.getEmail(), customer.getName(), hash(customer.getPassword()), customer.getPhone())), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody Customer customer, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to modify a customer account.");
        }
        if (customer == null)
        {
            throw new IllegalArgumentException("Invalid customer.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != customer.getScrsUserId()) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You cannot modify a customer account other than your own.");
        }
        if (customerService.getCustomerByID(customer.getScrsUserId()) == null)
        {
            throw new IllegalArgumentException("No such customer found.");
        }
        return new ResponseEntity<CustomerDto>(convertToDTO(customerService.updateCustomerInfo(customer)), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<CustomerDto> deleteCustomer(@RequestParam(value = "id") int customerID, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to delete a customer account.");
        }
        Customer customer = customerService.getCustomerByID(customerID);
        if (customer == null)
        {
            throw new IllegalArgumentException("Invalid customer. Please submit a valid customer account to be deleted.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != customerID) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You cannot delete a customer account other than your own.");
        }
        return new ResponseEntity<CustomerDto>(convertToDTO(customerService.deleteCustomer(customer)), HttpStatus.OK);
    }
}
