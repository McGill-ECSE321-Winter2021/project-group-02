package ca.mcgill.ecse321.scrs.controller;


import ca.mcgill.ecse321.scrs.dto.AppointmentDto;
import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDto;

@RestController
@RequestMapping(path = "/api/appointment")
public class AppointmentController
{
    @Autowired
    AppointmentService appointmentService;

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
}
