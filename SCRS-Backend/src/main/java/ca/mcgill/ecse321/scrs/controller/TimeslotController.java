package ca.mcgill.ecse321.scrs.controller;


import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.scrs.controller.Helper.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/timeslot")
public class TimeslotController
{

    @Autowired
    TimeslotService timeslotService;

    @GetMapping(value = {"/getServiceTimeslot", "/getServiceTimeslot/"})
    public ResponseEntity<List<TimeslotDto>> getServiceTimeslot(@RequestParam(name = "appointmentType") AppointmentType type)
    {
        // TODO find available timeslots based on selected appointment type
        return null;
    }

}
