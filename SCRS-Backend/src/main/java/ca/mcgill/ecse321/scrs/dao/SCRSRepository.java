package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.SCRS;
import org.springframework.data.repository.CrudRepository;

public interface SCRSRepository extends CrudRepository<SCRS, Integer>
{

    SCRS findByScrsId(int scrsId);
}
