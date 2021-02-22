package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.SCRSUser;
import org.springframework.data.repository.CrudRepository;

public interface SCRSUserRepository extends CrudRepository<SCRSUser, Integer> {
}
