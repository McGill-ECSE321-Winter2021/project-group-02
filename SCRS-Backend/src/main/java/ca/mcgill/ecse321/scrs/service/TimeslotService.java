package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AppointmentRepository;
import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class TimeslotService
{

    @Autowired
    TimeslotRepository timeslotRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Transactional
    public List<Timeslot> getAllTimeslots()
    {
        return toList(timeslotRepository.findAll());
    }

    @Transactional
    public List<Timeslot> getAvailableTimeslots(Date startDate, Date endDate)
    {
        List<Timeslot> timeslotsInPeriod = timeslotRepository.findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDate(startDate, endDate);
        timeslotsInPeriod.removeIf(timeslot -> appointmentRepository.existsByTimeslots(timeslot));
        return timeslotsInPeriod;
    }
    
    @Transactional    
    public void assignTechnicianToTimeslot(Technician tech, Timeslot ts)
    {
        if (tech == null) throw new IllegalArgumentException("Invalid technician");
        if (ts == null) throw new IllegalArgumentException("Invalid timeslot");

        ts.addTechnician(tech);
        timeslotRepository.save(ts);
    }
}
