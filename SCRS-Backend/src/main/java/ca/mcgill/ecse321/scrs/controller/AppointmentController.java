package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.dto.AppointmentDto;
import ca.mcgill.ecse321.scrs.model.Appointment;
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

    @PostMapping(value = {"/book", "/book/"})
    public ResponseEntity<AppointmentDto> bookAppointment(@RequestBody AppointmentDto appointmentDto)
    {
        if (appointmentDto == null)
        {
            throw new IllegalArgumentException("Invalid appointment. Please submit a valid appointment booking to be created.");
        }
        List<Timeslot> timeslots = timeslotService.getTimeslotsById(appointmentDto.getTimeslotsId());
        Appointment appointment = appointmentService.createAppointment(appointmentDto.getAppointmentType(),
                appointmentDto.getService(), appointmentDto.getNote(), appointmentDto.getPaymentStatus(),
                customerService.getCustomerByID(appointmentDto.getCustomerId()), timeslots.toArray(new Timeslot[0]));
        return new ResponseEntity<>(Helper.convertToDto(appointment), HttpStatus.OK);
    }

    @PutMapping(value = {"/pay", "/pay/"})
    public AppointmentDto payAppointment(@RequestParam(name = "appointmentId") int appointmentId)
    {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        appointment.setPaid(true);
        return new ResponseEntity<>(Helper.convertToDto(appointment), HttpStatus.OK);
    }

    @PutMapping(value = {"/rate-appointment", "/rate-appointment/"})
    public ResponseEntity<AppointmentDto> rateAppointment(@RequestParam(name = "appointmentId") int appointmentId, @RequestParam(name = "rating") int rating)
    {
        if (rating > 10 || rating < 0) throw new IllegalArgumentException("Invalid rating");

        Appointment appointment = appointmentService.rateAppointment(appointmentId, rating);
        return new ResponseEntity<>(Helper.convertToDto(appointment), HttpStatus.OK);
    }

    @PutMapping(value = {"/modifyAppointment", "/modifyAppointment/"})
    public ResponseEntity<AppointmentDto> modifyAppointment(@RequestBody AppointmentDto appointmentDto)
    {
        if (appointmentDto == null)
        {
            throw new IllegalArgumentException("Invalid appointment");
        }
        Appointment appointment = appointmentService.getAppointmentById(appointmentDto.getAppointmentId());
        appointmentService.modifyAppointment(appointment);
        return new ResponseEntity<>(Helper.convertToDto(appointment), HttpStatus.OK);
    }

}
