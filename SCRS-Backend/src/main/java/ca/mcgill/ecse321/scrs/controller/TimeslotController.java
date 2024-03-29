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

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDto;

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
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<TimeslotDto>> getTimeslots()
    {
        List<Timeslot> timeslots = timeslotService.getAllTimeslots();
        return new ResponseEntity<>(convertToDto(timeslots), HttpStatus.OK);
    }

    @GetMapping(value = {"/getTimeslot/{id}", "/getTimeslot/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TimeslotDto> getAllAvailableTimeslotsById(@PathVariable("id") int timeslotId)
    {
        try
        {
            Timeslot timeslot = timeslotService.getTimeslotById(timeslotId);
            if (timeslot == null)
            {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(convertToDto(timeslotService.getTimeslotById(timeslotId)), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping(value = {"/available/{startDate}", "/available/{startDate}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<TimeslotDto>> getAvailableTimeslot(@PathVariable("startDate") String startDate)
    {
        try
        {
            Date start = Date.valueOf(startDate);

            List<Timeslot> availableTimeslots = timeslotService.getAvailableTimeslots(start);
            return new ResponseEntity<>(convertToDto(availableTimeslots), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/assignTech", "/assignTech/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TimeslotDto> assignTechnicianToTimeslot(@RequestParam(name = "timeslotId") int timeslotId, @RequestParam(name = "technicianId") int technicianId)
    {
        Timeslot timeslot = timeslotService.getTimeslotById(timeslotId);
        if (timeslot == null)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try
        {
            Technician technician = technicianService.getTechnicianByID(technicianId);
            if (technician != null)
            {
                List<Technician> technicians = timeslot.getTechnicians();
                if (technicians != null)
                {
                    if (technicians.stream()
                            .anyMatch(alreadyAssignedTech -> alreadyAssignedTech.getScrsUserId() == technicianId))
                    {
                        // technician already assigned to timeslot
                        return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
                    }
                }

                timeslotService.assignTechnicianToTimeslot(technician, timeslot);
                return new ResponseEntity<>(convertToDto(timeslot), HttpStatus.OK);
            }
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = {"/create", "/create/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TimeslotDto> createTimeslot(@RequestBody TimeslotDto timeslotDto)
    {
        if (timeslotDto == null)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Workspace workspace = workspaceService.getWorkspaceById(timeslotDto.getWorkspaceId());
        try
        {
            Timeslot newTimeslot = timeslotService.createTimeslot(timeslotDto.getStartDate(), timeslotDto.getEndDate(), timeslotDto.getStartTime(), timeslotDto.getEndTime(), workspace);
            if (newTimeslot == null) return new ResponseEntity<>(new TimeslotDto(), HttpStatus.EXPECTATION_FAILED);
            return new ResponseEntity<>(convertToDto(newTimeslot), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(new TimeslotDto(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TimeslotDto> deleteTimeslot(@PathVariable("id") int timeslotID)
    {
        try
        {
            Timeslot timeslot = timeslotService.getTimeslotById(timeslotID);
            if (timeslot == null)
            {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(convertToDto(timeslotService.deleteTimeslot(timeslot)), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}