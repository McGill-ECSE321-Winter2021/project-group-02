package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimeslotRepository extends CrudRepository<Timeslot, Integer>
{
    Timeslot findByTimeSlotID(int id);

    List<Timeslot> findByWorkspace(Workspace ws);

    List<Timeslot> findByTechnicians(Technician technicians);


}
