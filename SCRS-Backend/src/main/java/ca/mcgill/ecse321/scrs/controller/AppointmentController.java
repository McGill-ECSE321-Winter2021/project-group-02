package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.AppointmentDto;
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.AppointmentService;
import ca.mcgill.ecse321.scrs.service.CustomerService;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
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

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

@RestController
@RequestMapping(path = "/api/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController
{

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    CustomerService customerService;
    @Autowired
    TimeslotService timeslotService;

    @GetMapping(path = {"/getall", "/getall/"})
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(@CookieValue(value = "id", defaultValue = "-1") String id) {
        if(id == null || id.equals("-1"))return new ResponseEntity<>(null, HttpStatus.OK);
        int ID = Integer.parseInt(id);

        List<Appointment> list = appointmentService.getAppointmentsByCustomer(customerService.getCustomerByID(ID));
        List<AppointmentDto> dtoList = new ArrayList<>();

        for (Appointment appointment : list)
        {
            dtoList.add(convertToDto(appointment));
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping(path = {"/notifications", "/notifications/"})
    public ResponseEntity<List<AppointmentDto>> notifications(@CookieValue(value = "id", defaultValue = "-1") String id) {
        if(id == null ||id.equals("-1"))return new ResponseEntity<>(null, HttpStatus.OK);
        int ID = Integer.parseInt(id);

        List<Appointment> list = appointmentService.getAppointmentsByCustomer(customerService.getCustomerByID(ID));

        //finding the same date next week
        Date now = new Date(LocalDate.now().toEpochDay());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, 7);
        Date nextWeek = new Date(calendar.getTimeInMillis());

        List<AppointmentDto> notificationList = new ArrayList<>();
        for (Appointment appointment : list)
        {
            List<Timeslot> timeslots = appointment.getTimeslots();
            ArrayList<Integer> newTimeslots = new ArrayList<>();
            for (Timeslot timeslot : timeslots)
            {
                if (timeslot.getStartDate().compareTo(now) >= 0 && timeslot.getStartDate().compareTo(nextWeek) <= 0)
                {
                    newTimeslots.add(timeslot.getTimeSlotID());
                }
            }
            if (!newTimeslots.isEmpty())
            {
                AppointmentDto appointmentDto = new AppointmentDto(appointment.getAppointmentID(), appointment.getAppointmentType().toString(), appointment.getService(), appointment.getNote(), appointment.getRating(), appointment.getFeedback(), appointment.getPaid(), appointment.getCustomer().getScrsUserId(), newTimeslots);
                notificationList.add(appointmentDto);
            }
        }

        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @PostMapping(value = {"/book", "/book/"})
    public ResponseEntity<AppointmentDto> bookAppointment(@RequestBody AppointmentDto appointmentDto)
    {
        if (appointmentDto == null)
        {
            throw new IllegalArgumentException("Invalid appointment. Please submit a valid appointment booking to be created.");
        }
        List<Timeslot> timeslots = timeslotService.getTimeslotsById(appointmentDto.getTimeslotsId());
        if (timeslots == null)
        {
            throw new IllegalArgumentException("Appointment does not have any time associated with it");
        }

        Customer customer = customerService.getCustomerByID(appointmentDto.getCustomerId());
        Appointment appointment = appointmentService.createAppointment(appointmentDto.getAppointmentType(),
                appointmentDto.getService(), appointmentDto.getNote(), appointmentDto.getPaymentStatus(),
               customer , timeslots.toArray(new Timeslot[0]));
        return new ResponseEntity<>(convertToDto(appointment), HttpStatus.OK);
    }

    @PutMapping(value = {"/pay", "/pay/"})
    public ResponseEntity<AppointmentDto> payAppointment(@RequestParam(name = "appointmentId") int appointmentId)
    {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        appointment.setPaid(true);
        return new ResponseEntity<>(convertToDto(appointment), HttpStatus.OK);
    }

    @PutMapping(value = {"/rate", "/rate/"})
    public ResponseEntity<AppointmentDto> rateAppointment(@RequestParam(name = "appointmentId") int appointmentId, @RequestParam(name = "rating") int rating)
    {
        if (rating > 10 || rating < 0)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            Appointment appointment = appointmentService.rateAppointment(appointmentId, rating);
            return new ResponseEntity<>(convertToDto(appointment), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/modifyAppointment", "/modifyAppointment/"})
    public ResponseEntity<AppointmentDto> modifyAppointment(@RequestBody AppointmentDto appointmentDto)
    {
        if (appointmentDto == null)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            Appointment modifiedAppt = appointmentService.modifyAppointment(appointmentDto);
            return new ResponseEntity<>(convertToDto(modifiedAppt), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
