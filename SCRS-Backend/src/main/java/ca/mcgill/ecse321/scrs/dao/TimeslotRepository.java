package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface
TimeslotRepository extends CrudRepository<Timeslot, Integer>
{
    Timeslot findByTimeSlotID(int id);

    List<Timeslot> findByWorkspace(Workspace ws);

    List<Timeslot> findByTechnicians(Technician technicians);

    List<Timeslot> findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDate(Date startDate, Date lastStartDate);

    List<Timeslot> findAllByTechniciansAndStartDateGreaterThanEqualAndAndStartDateLessThanEqualOrderByStartDate(Technician technician, Date startDate, Date lastStartDate);
}
