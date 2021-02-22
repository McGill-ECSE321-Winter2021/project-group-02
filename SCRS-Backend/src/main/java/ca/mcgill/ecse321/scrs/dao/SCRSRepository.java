package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import javax.persistence.EntityManager;

public interface SCRSRepository extends CrudRepository<SCRS, Integer>{

    SCRS findByScrsId(int scrsId);
}
