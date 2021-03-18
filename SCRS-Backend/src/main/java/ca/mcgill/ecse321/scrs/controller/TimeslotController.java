package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
import ca.mcgill.ecse321.scrs.service.TechnicianService;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import ca.mcgill.ecse321.scrs.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

@RestController
@RequestMapping(path = "/api/timeslot")
public class TimeslotController
{

    @Autowired
    TimeslotService timeslotService;
    @Autowired
    TechnicianService technicianService;
    @Autowired
    SCRSUserService scrsUserService;
    @Autowired
    WorkspaceService workspaceService;


    @GetMapping(value = {"/getTimeslots", "/getTimeslots/"})
    public ResponseEntity<List<TimeslotDto>> getTimeslots()
    {
        List<Timeslot> timeslots = timeslotService.getAllTimeslots();
        return new ResponseEntity<>(convertToDto(timeslots), HttpStatus.OK);
    }

    @GetMapping(value = {"/getAvailableTimeslot", "/getAvailableTimeslot/"})
    public ResponseEntity<List<TimeslotDto>> getAvailableTimeslot(@RequestParam(value = "startDate") Date startDate, @RequestParam(value = "endDate") Date endDate)
    {
        List<Timeslot> availableTimeslots = timeslotService.getAvailableTimeslots(startDate, endDate);
        return new ResponseEntity<>(convertToDto(availableTimeslots), HttpStatus.OK);
    }

    @PostMapping(value = {"/assignTechTimeslot", "/assignTechTimeslot/"})
    public ResponseEntity<TimeslotDto> assignTechnicianToTimeslot(@RequestBody TimeslotDto timeslotDto)
    {
        if (timeslotDto == null)
        {
            throw new IllegalArgumentException("Invalid timeslot");
        }
        Timeslot timeslot = timeslotService.getTimeslotById(timeslotDto.getTimeslotId());
        if (timeslot.getTechnicians().contains(timeslot))
        {
            throw new IllegalArgumentException("Technician already assigned to timeslot");
        }
        for(int technicianId: timeslotDto.getTechniciansId())
        {
            Technician technician = technicianService.getTechnicianByID(technicianId);
            timeslotService.assignTechnicianToTimeslot(technician, timeslot);
        }
        return new ResponseEntity<>(convertToDto(timeslot), HttpStatus.OK);
    }

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<TimeslotDto> createTimeslot(@RequestBody TimeslotDto timeslotDto)
    {
        if (timeslotDto == null)
        {
            throw new IllegalArgumentException("Invalid timeslot.");
        }

        Workspace workspace = workspaceService.getWorkspaceById(timeslotDto.getWorkspaceId());
        Timeslot newTimeslot = timeslotService.createTimeslot(timeslotDto.getStartDate(),timeslotDto.getEndDate(),timeslotDto.getStartTime(),timeslotDto.getEndTime(),workspace);
        return new ResponseEntity<>(convertToDto(newTimeslot),HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete","/delete/"})
    public ResponseEntity<TimeslotDto> deleteTimeslot(@RequestParam(value = "id") int timeslotID, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to delete a timeslot.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)))
        {
            throw new IllegalArgumentException("You do not have permission to delete a timeslot.");
        }
        Timeslot timeslot = timeslotService.getTimeslotById(timeslotID);
        if (timeslot == null)
        {
            throw new IllegalArgumentException("Invalid timeslot. Please submit a valid timeslot to be deleted.");
        }
        return new ResponseEntity<>(convertToDto(timeslotService.deleteTimeslot(timeslot)), HttpStatus.OK);
    }
}