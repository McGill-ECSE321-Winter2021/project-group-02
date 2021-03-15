package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TechnicianRepository extends CrudRepository<Technician, Integer> {

    Technician findByScrsUserId(int id);

    Technician findByEmail(String email);

    Technician findByName(String name);

    Technician findByPhone(String phone);
}
