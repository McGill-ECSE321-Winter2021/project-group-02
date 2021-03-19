package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.*;
import org.springframework.data.repository.CrudRepository;

public interface AssistantRepository extends CrudRepository<Assistant, Integer>
{

    Assistant findByScrsUserId(int id);

    Assistant findByEmail(String email);

    Assistant findByName(String name);

    Assistant findByPhone(String phone);

}
