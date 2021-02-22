package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Timeslot;
import org.springframework.data.repository.CrudRepository;

public interface TimeslotRepository extends CrudRepository<Timeslot, Integer> {
    Timeslot findByTimeSlotID(int id);
}
