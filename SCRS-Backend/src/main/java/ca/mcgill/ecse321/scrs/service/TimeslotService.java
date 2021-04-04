package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AppointmentRepository;
import ca.mcgill.ecse321.scrs.dao.TechnicianRepository;
import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
import ca.mcgill.ecse321.scrs.dao.WorkspaceRepository;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.*;

@Service
public class TimeslotService
{

    @Autowired
    TimeslotRepository timeslotRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    TechnicianRepository technicianRepository;

    @Transactional
    public Timeslot createTimeslot(Date startDate, Date endDate, Time startTime, Time endTime, Workspace workspace)
    {
        checkDateValidity(startDate, endDate);
        checkTimeValidity(startTime, endTime);
        if (workspace == null || workspaceRepository.findByWorkspaceID(workspace.getWorkspaceID()) == null)
            throw new IllegalArgumentException("Please input a valid workspace.");
        Timeslot timeslot = new Timeslot();
        timeslot.setStartDate(startDate);
        timeslot.setEndDate(endDate);
        timeslot.setStartTime(startTime);
        timeslot.setEndTime(endTime);
        timeslot.setWorkspace(workspace);
        timeslotRepository.save(timeslot);
        return timeslot;
    }

    @Transactional
    public List<Timeslot> getAllTimeslots()
    {
        return toList(timeslotRepository.findAll());
    }

    @Transactional
    public Timeslot getTimeslotById(int id)
    {
        return timeslotRepository.findByTimeSlotID(id);
    }

    @Transactional
    public List<Timeslot> getTimeslotsById(List<Integer> timeslotsId)
    {
        if (timeslotsId == null || timeslotsId.size() == 0)
            throw new IllegalArgumentException("Please input at least one valid timeslot ID.");
        ArrayList<Timeslot> timeslots = new ArrayList<>();
        for (int id : timeslotsId)
        {
            timeslots.add(timeslotRepository.findByTimeSlotID(id));
        }
        return timeslots;
    }

    @Transactional
    public List<Timeslot> getTimeslotsByTechnicianBetweenDates(Technician technician, Date startDate, Date endDate)
    {
        checkDateValidity(startDate, endDate);
        if (technician == null || technicianRepository.findByScrsUserId(technician.getScrsUserId()) == null)
            throw new IllegalArgumentException("Invalid technician.");
        List<Timeslot> timeslotsInPeriod = timeslotRepository.findAllByTechniciansAndStartDateGreaterThanEqualAndAndStartDateLessThanEqualOrderByStartDate(technician, startDate, endDate);
        List<Timeslot> technicianTimeslots = toList(timeslotRepository.findByTechnicians(technician));
        technicianTimeslots.retainAll(timeslotsInPeriod);
        return timeslotsInPeriod;
    }

    @Transactional
    public List<Timeslot> getTimeslotsByWorkspace(Workspace workspace)
    {
        if (workspace == null || workspaceRepository.findByWorkspaceID(workspace.getWorkspaceID()) == null)
            throw new IllegalArgumentException("Invalid workspace.");
        return toList(timeslotRepository.findByWorkspace(workspace));
    }

    @Transactional
    public List<Timeslot> getAvailableTimeslots(Date startDate)
    {
        List<Timeslot> timeslotsInPeriod = timeslotRepository.findAllByStartDateGreaterThanEqualOrderByStartDate(startDate);
        timeslotsInPeriod.removeIf(timeslot -> appointmentRepository.existsByTimeslots(timeslot));
        return timeslotsInPeriod;
    }

    @Transactional
    public boolean assignTechnicianToTimeslot(Technician tech, Timeslot ts)
    {
        if (tech == null || technicianRepository.findByScrsUserId(tech.getScrsUserId()) == null)
            throw new IllegalArgumentException("Invalid technician.");
        if (ts == null || timeslotRepository.findByTimeSlotID(ts.getTimeSlotID()) == null)
            throw new IllegalArgumentException("Invalid timeslot.");
        if (ts.getTechnicians() != null && ts.getTechnicians().contains(tech)) return false;
        ts.addTechnician(tech);
        timeslotRepository.save(ts);
        return true;
    }

    @Transactional
    public Timeslot deleteTimeslot(Timeslot timeslot)
    {
        timeslotRepository.delete(timeslot);
        return timeslot;
    }

}
