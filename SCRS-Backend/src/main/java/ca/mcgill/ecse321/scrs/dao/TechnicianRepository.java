package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Technician;
import org.springframework.data.repository.CrudRepository;

public interface TechnicianRepository extends CrudRepository<Technician, Integer>
{

    Technician findByScrsUserId(int id);

    Technician findByEmail(String email);

    Technician findByName(String name);

    Technician findByPhone(String phone);

}
