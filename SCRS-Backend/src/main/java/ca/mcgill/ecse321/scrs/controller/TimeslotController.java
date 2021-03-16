package ca.mcgill.ecse321.scrs.controller;


import ca.mcgill.ecse321.scrs.dto.TechnicianDto;
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.scrs.controller.Helper.*;

import java.sql.Date;
import java.util.List;

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDTO;

@RestController
@RequestMapping(path = "/api/timeslot")
public class TimeslotController
{

    @Autowired
    TimeslotService timeslotService;

    @GetMapping(value = {"/getTimeslots", "/getTimeslots/"})
    public ResponseEntity<List<TimeslotDto>> getTimeslots()
    {
        List<Timeslot> timeslots = timeslotService.getAllTimeslots();
        return new ResponseEntity<List<TimeslotDto>>(Helper.convertToDto(timeslots), HttpStatus.OK);
    }

    @GetMapping(value = {"/getAvailableTimeslot", "/getAvailableTimeslot/"})
    public ResponseEntity<List<TimeslotDto>> getServiceTimeslot(@RequestParam(name = "startDate") Date startDate, @RequestParam(name = "endDate") Date endDate)
    {
        List<Timeslot> availableTimeslots = timeslotService.getAvailableTimeslots(startDate, endDate);
        return new ResponseEntity<List<TimeslotDto>>(Helper.convertToDto(availableTimeslots), HttpStatus.OK);
    }

    @PostMapping(value = {"/assignTechTimeslot", "/assignTechTimeslot/"})
    public ResponseEntity<TechnicianDto> assignTechnicianToTimeslot(@RequestBody Technician technician, @RequestBody Timeslot timeslot) {
        if (technician == null) throw new IllegalArgumentException("Invalid technician");
        if (timeslot == null) throw new IllegalArgumentException("Invalid timeslot");

        if (timeslot.getTechnicians().contains(timeslot)) throw new IllegalArgumentException("Technician already assigned to timeslot");

        timeslotService.assignTechnicianToTimeslot(technician, timeslot);

        return new ResponseEntity<>(convertToDTO(technician), HttpStatus.OK);
    }
}
