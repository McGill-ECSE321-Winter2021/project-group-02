package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.AppointmentDto;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDto;

@RestController
@RequestMapping(path = "/api/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @GetMapping("/getall/{id}")
    public ResponseEntity<ArrayList<Appointment>> getAll(@PathVariable("id") String id) {
        int ID = Integer.parseInt(id);
        return new ResponseEntity<>(new ArrayList<Appointment>(), HttpStatus.OK);
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<ArrayList<Appointment>> notifications(@PathVariable("id") String id) {
        int ID = Integer.parseInt(id);
        return new ResponseEntity<>(new ArrayList<Appointment>(), HttpStatus.OK);
    }

    @PostMapping(value = { "/book", "/book/" })
    public AppointmentDto bookAppointment(@RequestBody Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException(
                    "Invalid appointment. Please submit a valid appointment booking to be created.");
        }
        Appointment a = appointmentService.createAppointment(appointment.getAppointmentType(), appointment.getService(),
                appointment.getNote(), appointment.getPaid(), appointment.getCustomer(),
                appointment.getTimeslots().toArray(new Timeslot[0]));
        return convertToDto(a);
    }

    @PutMapping(value = { "/pay", "/pay/" })
    public AppointmentDto payAppointment(@RequestParam(name = "appointmentId") int appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        appointment.setPaid(true);
        return convertToDto(appointment);
    }
    
    @PutMapping(value = {"/rate-appointment", "/rate-appointment/"})
    public ResponseEntity<AppointmentDto> rateAppointment(@RequestParam(name = "appointmentId") int appointmentId, @RequestParam(name = "rating") int rating) {
        if (rating > 10 || rating < 0) throw new IllegalArgumentException("Invalid rating");

        Appointment appointment = appointmentService.rateAppointment(appointmentId, rating);
        return new ResponseEntity<>(convertToDto(appointment), HttpStatus.OK);
    }

    @PutMapping(value = {"/modifyAppointment", "/modifyAppointment/"})
    public ResponseEntity<AppointmentDto> modifyAppointment(@RequestBody Appointment appt) {
        if (appt == null) throw new IllegalArgumentException("Invalid appointment");
        return new ResponseEntity<>(convertToDto(appointmentService.modifyAppointment(appt)), HttpStatus.OK);
    }

}
