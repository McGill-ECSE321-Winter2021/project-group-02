package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.AppointmentDto;
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.dto.WorkspaceDto;
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
import java.sql.Time;
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
        // if(id == null)return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        // int ID = Integer.parseInt(id);

        // Customer customer = customerService.getCustomerByID(ID);
        // if(customer == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);

        // List<Appointment> list = appointmentService.getAppointmentsByCustomer(customer);
        // List<AppointmentDto> dtoList = new ArrayList<>();

        // if(list != null){
        //     for (Appointment appointment : list)
        //     {
        //         dtoList.add(convertToDto(appointment));
        //     }
        // }
        // return new ResponseEntity<>(dtoList, HttpStatus.OK);

        
        ArrayList<AppointmentDto> dtoList = new ArrayList<>();

// int id, String type, String service, String note, int customerId, ArrayList<Integer> timeslotsId)

        ArrayList<Integer> array=new ArrayList<Integer>(10);
        array.add(1);
        array.add(2);

        ArrayList<AppointmentDto> dtoList2 = new ArrayList<>();
        ArrayList<Integer> array2=new ArrayList<Integer>(10);
        array.add(3);
        array.add(5);

        dtoList.add(new AppointmentDto(3, Appointment.AppointmentType.CarWash.toString(),"service","note",8,array));

        dtoList.add(new AppointmentDto(4, Appointment.AppointmentType.Maintenance.toString(),"service","note2",8,array2));

        return new ResponseEntity<>(dtoList, HttpStatus.OK);

    }

    @GetMapping(path = {"/getallappointments", "/getallappointments/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
//
//         List<Appointment> list = appointmentService.getAllAppointments();
//         List<AppointmentDto> dtoList = new ArrayList<>();
//
//         if(list != null){
//             for (Appointment appointment : list)
//             {
//                 dtoList.add(convertToDto(appointment));
//             }
//         }
//         return new ResponseEntity<>(dtoList, HttpStatus.OK);


        ArrayList<AppointmentDto> dtoList = new ArrayList<>();

// int id, String type, String service, String note, int customerId, ArrayList<Integer> timeslotsId)

        ArrayList<Integer> array=new ArrayList<Integer>(10);
        array.add(1);
        array.add(2);

        ArrayList<AppointmentDto> dtoList2 = new ArrayList<>();
        ArrayList<Integer> array2=new ArrayList<Integer>(10);
        array.add(3);
        array.add(5);

        dtoList.add(new AppointmentDto(3, Appointment.AppointmentType.CarWash.toString(),"service","note",8,array));

        dtoList.add(new AppointmentDto(4, Appointment.AppointmentType.Maintenance.toString(),"service","note2",8,array2));
        dtoList.add(new AppointmentDto(5, Appointment.AppointmentType.Maintenance.toString(),"service","note2",8,array2));


        dtoList.add(new AppointmentDto(6, Appointment.AppointmentType.Maintenance.toString(),"service","note2",6,array2));

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
//        if(id == null)return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
//        int ID = Integer.parseInt(id);
//
//        Customer customer = customerService.getCustomerByID(ID);
//        if(customer == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
//
//        List<Appointment> list = appointmentService.getAppointmentsByCustomer(customer);
//
//        if(list != null){
//            //finding the same date next week
//            Date now = new Date(LocalDate.now().toEpochDay());
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(now);
//            calendar.add(Calendar.DATE, 7);
//            Date nextWeek = new Date(calendar.getTimeInMillis());
//
//            List<AppointmentDto> notificationList = new ArrayList<>();
//            for (Appointment appointment : list)
//            {
//                List<Timeslot> timeslots = appointment.getTimeslots();
//                ArrayList<Integer> newTimeslots = new ArrayList<>();
//                for (Timeslot timeslot : timeslots)
//                {
//                    if (timeslot.getStartDate().compareTo(now) >= 0 && timeslot.getStartDate().compareTo(nextWeek) <= 0)
//                    {
//                        newTimeslots.add(timeslot.getTimeSlotID());
//                    }
//                }
//                if (!newTimeslots.isEmpty())
//                {
//                    AppointmentDto appointmentDto = new AppointmentDto(appointment.getAppointmentID(), appointment.getAppointmentType().toString(), appointment.getService(), appointment.getNote(), appointment.getRating(), appointment.getFeedback(), appointment.getPaid(), appointment.getCustomer().getScrsUserId(), newTimeslots);
//                    notificationList.add(appointmentDto);
//                }
//            }

        //test

        ArrayList<AppointmentDto> dtoList = new ArrayList<>();

// int id, String type, String service, String note, int customerId, ArrayList<Integer> timeslotsId)

        ArrayList<Integer> array=new ArrayList<Integer>(10);
        array.add(1);
        array.add(2);

        ArrayList<AppointmentDto> dtoList2 = new ArrayList<>();
        ArrayList<Integer> array2=new ArrayList<Integer>(10);
        array.add(3);
        array.add(5);

        dtoList.add(new AppointmentDto(3, Appointment.AppointmentType.CarWash.toString(),"service","note",8,array));

        dtoList.add(new AppointmentDto(4, Appointment.AppointmentType.Maintenance.toString(),"service","note2",8,array2));

        return new ResponseEntity<>(dtoList, HttpStatus.OK);

//        return new ResponseEntity<>(notificationList, HttpStatus.OK);
//        }else return new ResponseEntity<>(null, HttpStatus.OK);
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

 @GetMapping(value = {"/getStartAndEnd/{id}","/getStartAndEnd/{id}/"})
 @CrossOrigin(origins = "*")
 public ResponseEntity<TimeslotDto> getAppointmentStartAndEnd(@PathVariable("id") int appointmentId)
    {
        try {
//            Appointment appointment= appointmentService.getAppointmentById(appointmentId);
//
//            if(appointment==null)
//            {
//                System.out.println("output1");
//
//                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        ArrayList<Timeslot> timeslots = new ArrayList<>(appointmentService.getAppointmentById(appointmentId).getTimeslots());
//
//        if (timeslots.size() == 0) {
//            System.out.println("output2");
//
//            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        Date minD= timeslots.get(0).getStartDate();
//        Date maxD= timeslots.get(0).getEndDate();
//
//        Time minT= timeslots.get(0).getStartTime();
//        Time maxT = timeslots.get(0).getEndTime();
//
//        for (Timeslot t : timeslots) {
//        if(t.getEndDate().after(maxD)){
//            maxD = t.getEndDate();
//            maxT = t.getEndTime();
//
//        } else if (t.getEndDate().equals(maxD)) {
//            if(t.getEndTime().after(maxT)) maxT = t.getEndTime();
//        } else if (t.getStartDate().before(minD)){
//            minD = t.getStartDate();
//            minT = t.getStartTime();
//        } else if (t.getStartDate().equals(minD)) {
//            if (t.getStartTime().before(minT)) minT = t.getStartTime();
//        }
//        }

//        ArrayList<AppointmentDto> dtoList = new ArrayList<>();
//
//// int id, String type, String service, String note, int customerId, ArrayList<Integer> timeslotsId)
//
//        ArrayList<Integer> array=new ArrayList<Integer>(10);
//        array.add(1);
//        array.add(2);
//
//
//        dtoList.add(new AppointmentDto(3, Appointment.AppointmentType.CarWash.toString(),"service","note",8,array));
//
//        Date firstDate = new Date(2020,3,21);
//        Date lastDate = new Date(2020,3,21);
//
//        Time firstTime = Time.valueOf("18:45:20");
//        Time lastTime = Time.valueOf("19:05:20");
//
            long millis=System.currentTimeMillis();
            Date firstDate =new java.sql.Date(millis);
            Date lastDate =new java.sql.Date(millis);

            Time firstTime = Time.valueOf("18:45:20");
            Time lastTime = Time.valueOf("19:05:20");
        TimeslotDto send= new TimeslotDto(3,firstDate,lastDate,firstTime,lastTime,5);

            //TimeslotDto output = new TimeslotDto(-1,minD,maxD,minT,maxT,-1);
            System.out.println("output3");
            //return new ResponseEntity<>(output, HttpStatus.OK); //B-baka timeslotDto only for dates UwO
            return new ResponseEntity<>(send, HttpStatus.OK); //B-baka timeslotDto only for dates UwO

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
