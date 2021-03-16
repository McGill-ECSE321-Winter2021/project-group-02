package ca.mcgill.ecse321.scrs.controller;


import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

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

}
