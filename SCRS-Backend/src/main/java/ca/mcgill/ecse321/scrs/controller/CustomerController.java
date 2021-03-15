package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    @PostMapping(value = {"/create", "/create/"})
    public CustomerDto createCustomer(@RequestBody Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("Invalid customer. Please submit a valid customer account to be created.");
        }
        if (customerService.getCustomerByEmail(customer.getEmail()) != null)
        {
            throw new IllegalArgumentException("Email already in use, please try a different email address.");
        }
        return convertToDTO(customerService.createCustomer(customer.getEmail(), customer.getName(), customer.getPassword(), customer.getPhone()));
    }

    // ================= Helper Methods ================

    public static CustomerDto convertToDTO(Customer c)
    {
        if (c == null) throw new IllegalArgumentException("There is no such customer!");
        return new CustomerDto(c.getScrsUserId(), c.getName(), c.getEmail(), c.getPhone());
    }
}
