package ca.mcgill.ecse321.scrs.controller;


import ca.mcgill.ecse321.scrs.dto.AppointmentDto;
import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController
{
    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/getall/{id}")
    public ResponseEntity<ArrayList<Appointment>> getAll(@PathVariable("id") String id)
    {
        int ID = Integer.valueOf(id);
        return new ResponseEntity<>(new ArrayList<Appointment>(), HttpStatus.OK);
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<ArrayList<Appointment>> notifications(@PathVariable("id") String id)
    {
        int ID = Integer.valueOf(id);
        return new ResponseEntity<>(new ArrayList<Appointment>(), HttpStatus.OK);
    }

    @PostMapping(value = {"/book", "/book/"})
    public AppointmentDto bookAppointment(@RequestBody Appointment appointment)
    {
        if (appointment == null)
        {
            throw new IllegalArgumentException("Invalid appointment. Please submit a valid appointment booking to be created.");
        }
        Appointment a = appointmentService.createAppointment(appointment.getAppointmentType(), appointment.getService(), appointment.getNote(), appointment.getPaid(), appointment.getCustomer(), appointment.getTimeslots().toArray(new Timeslot[0]));
        return convertToDto(a);
    }

    @PutMapping(value = {"/pay", "/pay/"})
    public AppointmentDto payAppointment(@RequestParam(name = "appointmentId") int appointmentId)
    {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        appointment.setPaid(true);
        return convertToDto(appointment);
    }

    // ========== Helper Methods ==========

    public static AppointmentDto convertToDto(Appointment a)
    {
        if (a == null)
            throw new IllegalArgumentException("There is no such appointment!");
        CustomerDto customerDto = CustomerController.convertToDTO(a.getCustomer());
        List<TimeslotDto> timeslots = TimeslotController.convertToDto(a.getTimeslots());
        return new AppointmentDto(a.getAppointmentID(), a.getAppointmentType(), a.getService(), a.getNote(), customerDto, timeslots);
    }
}