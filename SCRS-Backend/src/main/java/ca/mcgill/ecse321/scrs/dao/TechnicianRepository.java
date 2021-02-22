package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Technician;
import org.springframework.data.repository.CrudRepository;

public interface TechnicianRepository extends CrudRepository<Technician, Integer>
{
}
