package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer>
{

    Customer findByScrsUserId(int id);

    Customer findByEmail(String email);

    Customer findByName(String name);

    Customer findByPhone(String phone);
}
