package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class TimeslotService {

    @Autowired
    TimeslotRepository timeslotRepository;

    @Transactional
    public List<Timeslot> getAllTimeslots() {
        return toList(timeslotRepository.findAll());
    }

    @Transactional
<<<<<<< Updated upstream
    public void assignTechnicianToTimeslot(Technician tech, Timeslot ts) {
        if (tech == null) throw new IllegalArgumentException("Invalid technician");
        if (ts == null) throw new IllegalArgumentException("Invalid timeslot");

        ts.addTechnician(tech);
        timeslotRepository.save(ts);
    }
=======
    public List<Timeslot> getTimeslotsByTechnician(Technician technician) {
        return toList(timeslotRepository.findByTechnicians(technician));
    }
    @Transactional
    public Timeslot createTimeslot(Date startDate, Date endDate, Time startTime, Time endTime, Workspace workspace) {
        Timeslot timeslot = new Timeslot();
        timeslot.setStartDate(startDate);
        timeslot.setEndDate(endDate);
        timeslot.setStartTime(startTime);
        timeslot.setWorkspace(workspace);
        timeslotRepository.save(timeslot);
        return timeslot;
    }

>>>>>>> Stashed changes
}
