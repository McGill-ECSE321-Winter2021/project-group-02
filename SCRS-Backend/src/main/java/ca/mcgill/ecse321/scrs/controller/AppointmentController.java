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


    @GetMapping(path = {"/getall/{id}", "/getall/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(@PathVariable String id) {
        if(id == null)return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        int ID = Integer.parseInt(id);

        Customer customer = customerService.getCustomerByID(ID);
        if(customer == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);

        List<Appointment> list = appointmentService.getAppointmentsByCustomer(customer);
        List<AppointmentDto> dtoList = new ArrayList<>();

        if(list != null){
            for (Appointment appointment : list)
            {
                dtoList.add(convertToDto(appointment));
            }
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping(path = {"/getById/{id}", "/getById/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable String id)
    {
        if(id == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);

        Appointment appointment = appointmentService.getAppointmentById(Integer.parseInt(id));

        if (appointment == null)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(convertToDto(appointment), HttpStatus.OK);
    }

    @GetMapping(path = {"/notifications/{id}", "/notifications/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<AppointmentDto>> notifications(@PathVariable String id) {
        if(id == null)return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        int ID = Integer.parseInt(id);

        Customer customer = customerService.getCustomerByID(ID);
        if(customer == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);

        List<Appointment> list = appointmentService.getAppointmentsByCustomer(customer);

        if(list != null){
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
        }else return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(value = {"/book", "/book/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<AppointmentDto> bookAppointment(@RequestBody AppointmentDto appointmentDto)
    {
        if (appointmentDto == null)
        {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        try {
            List<Timeslot> timeslots = timeslotService.getTimeslotsById(appointmentDto.getTimeslotsId());
            if (timeslots == null)
            {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
            Customer customer = customerService.getCustomerByID(appointmentDto.getCustomerId());
            Appointment appointment = appointmentService.createAppointment(appointmentDto.getAppointmentType(),
                    appointmentDto.getService(), appointmentDto.getNote(), appointmentDto.getPaymentStatus(),
                    customer, timeslots.toArray(new Timeslot[0]));
            return new ResponseEntity<>(convertToDto(appointment), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/pay", "/pay/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<AppointmentDto> payAppointment(@RequestParam(name = "appointmentId") int appointmentId)
    {
        try
        {
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            if (appointment == null) {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
            appointment.setPaid(true);
            appointment = appointmentService.modifyAppointment(convertToDto(appointment));
            return new ResponseEntity<>(convertToDto(appointment), HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/rate", "/rate/"})
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
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
