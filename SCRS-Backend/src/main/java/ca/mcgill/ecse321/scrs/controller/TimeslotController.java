package ca.mcgill.ecse321.scrs.controller;


import ca.mcgill.ecse321.scrs.dto.TechnicianDto;
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import ca.mcgill.ecse321.scrs.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.scrs.controller.Helper.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

@RestController
@RequestMapping(path = "/api/timeslot")
public class TimeslotController
{

    @Autowired
    TimeslotService timeslotService;

    @Autowired
    SCRSUserService scrsUserService;

    @GetMapping(value = {"/getServiceTimeslot", "/getServiceTimeslot/"})
    public List<TimeslotDto> getServiceTimeslot(@RequestParam(name = "appointmentType") AppointmentType type)
    {
        // TODO find available timeslots based on selected appointment type
        return null;
    }

    @PostMapping(value = {"/assignTechTimeslot", "/assignTechTimeslot/"})
    public ResponseEntity<TechnicianDto> assignTechnicianToTimeslot(@RequestBody Technician technician, @RequestBody Timeslot timeslot)
    {
        if (technician == null) throw new IllegalArgumentException("Invalid technician");
        if (timeslot == null) throw new IllegalArgumentException("Invalid timeslot");

        if (timeslot.getTechnicians().contains(timeslot))
            throw new IllegalArgumentException("Technician already assigned to timeslot");

        timeslotService.assignTechnicianToTimeslot(technician, timeslot);

        return new ResponseEntity<>(convertToDTO(technician), HttpStatus.OK);
    }

    @PostMapping(value = {"/create/{id}"})
    public ResponseEntity<TimeslotDto> createTimeslot(@PathVariable("id") int timeslotID, @RequestBody Workspace workspace, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to create a timeslot.");
        }
        if (workspace == null)
        {
            throw new IllegalArgumentException("Invalid workspace.");
        }
        Timeslot timeslot = timeslotService.getTimeslotById(timeslotID);
        if (timeslot == null)
        {
            throw new IllegalArgumentException("Invalid timeslot. Please submit a valid timeslot to be created.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You do not have permission to create a timeslot.");
        }
        if (workspace.getAvailabilities().contains(timeslot)){
            throw new IllegalArgumentException("This timeslot has already been created");
        }
        return new ResponseEntity<>(convertToDto(timeslotService.createTimeslot(timeslot.getStartDate(),timeslot.getEndDate(),timeslot.getStartTime(),timeslot.getEndTime(),workspace)),HttpStatus.OK);

    }

    @DeleteMapping(value = {"/delete/{id}"})
    public ResponseEntity<TimeslotDto> deleteTimeslot(@PathVariable("id") int timeslotID, @RequestBody Workspace workspace, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to delete a timeslot.");
        }
        Timeslot timeslot = timeslotService.getTimeslotById(timeslotID);
        if (workspace == null)
        {
            throw new IllegalArgumentException("Invalid workspace.");
        }
        if (timeslot == null)
        {
            throw new IllegalArgumentException("Invalid technician. Please submit a valid timeslot to be deleted.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)))
        {
            throw new IllegalArgumentException("You do not have permission to delete a timeslot.");
        }
        if (!workspace.getAvailabilities().contains(timeslot))
        {
            throw new IllegalArgumentException("Timeslot not assignment");
        }
        return new ResponseEntity<>(convertToDto(timeslotService.deleteTimeslot(timeslot)), HttpStatus.OK);
    }


}
