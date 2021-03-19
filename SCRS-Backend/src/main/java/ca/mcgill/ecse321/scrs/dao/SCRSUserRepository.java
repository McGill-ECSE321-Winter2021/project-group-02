package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.SCRSUser;
import org.springframework.data.repository.CrudRepository;

public interface SCRSUserRepository extends CrudRepository<SCRSUser, Integer>
{
    SCRSUser findByScrsUserId(int Id);

    Customer findByEmail(String email);

    Customer findByName(String name);

    Customer findByPhone(String phone);
}
