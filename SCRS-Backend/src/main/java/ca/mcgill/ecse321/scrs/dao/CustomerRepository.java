package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
