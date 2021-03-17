package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.dto.AppointmentDto;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDto;

@RestController
@RequestMapping(path = "/api/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController
{
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/getall")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(@CookieValue(value = "id", defaultValue = "-1") String id) {
        if(id.equals("-1") || id == null)return new ResponseEntity<>(null, HttpStatus.OK);
        int ID = Integer.parseInt(id);

        List<Appointment> list = appointmentService.getAppointmentsByCustomer(customerRepository.findByScrsUserId(ID));
        List<AppointmentDto> dtoList = new ArrayList<>();

        for(int i = 0 ; i < list.size() ; i++){
            dtoList.add(convertToDto(list.get(i)));
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<AppointmentDto>> notifications(@CookieValue(value = "id", defaultValue = "-1") String id) {
        if(id.equals("-1") || id == null)return new ResponseEntity<>(null, HttpStatus.OK);
        int ID = Integer.parseInt(id);

        List<Appointment> list = appointmentService.getAppointmentsByCustomer(customerRepository.findByScrsUserId(ID));

        //finding the same date next week
        Date now = new Date(LocalDate.now().toEpochDay());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, 7);
        Date nextWeek = new Date(calendar.getTimeInMillis());

        List<AppointmentDto> notificationList = new ArrayList<>();
        for(int i = 0 ; i < list.size() ; i++){
            List<Timeslot> timeslots = list.get(i).getTimeslots();
            AppointmentDto appointmentDto = convertToDto(list.get(i));
            for(int j = 0 ; j < timeslots.size() ; j++){
                if(timeslots.get(j).getStartDate().compareTo(now) < 0 && timeslots.get(j).getStartDate().compareTo(nextWeek) > 0){
                    appointmentDto.getTimeslots().remove(j);
                }
            }
            if(!appointmentDto.getTimeslots().isEmpty()) notificationList.add(appointmentDto);
        }

        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @PostMapping(value = { "/book", "/book/" })
    public ResponseEntity<AppointmentDto> bookAppointment(@RequestBody Appointment appointment) {
        if (appointment == null) {

            throw new IllegalArgumentException(
                    "Invalid appointment. Please submit a valid appointment booking to be created.");
        }
        Appointment a = appointmentService.createAppointment(appointment.getAppointmentType(), appointment.getService(),
                appointment.getNote(), appointment.getPaid(), appointment.getCustomer(),
                appointment.getTimeslots().toArray(new Timeslot[0]));
        return new ResponseEntity<AppointmentDto>(convertToDto(a), HttpStatus.OK);
    }

    @PutMapping(value = { "/pay", "/pay/" })
    public ResponseEntity<AppointmentDto> payAppointment(@RequestParam(name = "appointmentId") int appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        appointment.setPaid(true);
        return new ResponseEntity<>(convertToDto(appointment), HttpStatus.OK);

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

        appointmentService.modifyAppointment(appt);
        return new ResponseEntity<>(convertToDto(appt), HttpStatus.OK);
    }

}
