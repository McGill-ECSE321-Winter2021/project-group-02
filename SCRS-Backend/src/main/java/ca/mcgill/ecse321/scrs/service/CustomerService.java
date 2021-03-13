package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String email, String name, String password, String phone) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setName(name);
        customer.setPassword(password);
        customer.setPhone(phone);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return toList(customerRepository.findAll());
    }

    @Transactional
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public Customer getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    @Transactional
    public Customer getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Transactional
    public Customer getCustomerByAppointments(List<Appointment> appointments) {
        return customerRepository.findByAppointments(appointments);
    }
}
