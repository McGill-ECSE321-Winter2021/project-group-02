package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.TechnicianDto;
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
import ca.mcgill.ecse321.scrs.service.TechnicianService;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

@RestController
@RequestMapping(path = "/api/technician")
public class TechnicianController
{
    @Autowired
    TechnicianService technicianService;
    @Autowired
    SCRSUserService scrsUserService;
    @Autowired
    TimeslotService timeslotService;

    @PostMapping(value = {"/create", "/create/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TechnicianDto> createTechnician(@RequestBody Technician technician, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            // TODO handle no login error with cookies (uncomment next line)
            //return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.UNAUTHORIZED);
            // Please login to create a technician account.
        }
        if (technician == null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.EXPECTATION_FAILED);
            // Invalid technician. Please submit a valid technician account to be created.
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            // TODO handle bad login error with cookies (uncomment next line)
            //return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.UNAUTHORIZED);
            // You do not have permission to create a technician account.
        }
        if (scrsUserService.getSCRSUserByEmail(technician.getEmail()) != null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.ALREADY_REPORTED);
            // Email already in use, please try a different email address.
        }
        Technician newTechnician = technicianService.createTechnician(technician.getEmail(), technician.getName(), hash(technician.getPassword()), technician.getPhone());
        return new ResponseEntity<>(convertToDto(newTechnician), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TechnicianDto> updateAssistant(@RequestBody Technician technician, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            // TODO handle no login error with cookies (uncomment next line)
            //return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.UNAUTHORIZED);
            // Please login to modify a technician account.
        }
        if (technician == null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.EXPECTATION_FAILED);
            // Invalid technician.
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != technician.getScrsUserId()) //does not have permission to edit.
        {
            // TODO handle bad login error with cookies (uncomment next line)
            //return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.UNAUTHORIZED);
            // You do not have permission to create a technician account.
        }
        if (technicianService.getTechnicianByID(technician.getScrsUserId()) == null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.NOT_ACCEPTABLE);
            // No such technician found.
        }
        if (scrsUserService.getSCRSUserByEmail(technician.getEmail()) != null && scrsUserService.getSCRSUserByEmail(technician.getEmail()).getScrsUserId() != technician.getScrsUserId())
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.ALREADY_REPORTED);
            // Email already in use, please try a different email address.
        }
        Technician updatedTechnician = technicianService.updateTechnicianInfo(technician);
        return new ResponseEntity<>(convertToDto(updatedTechnician), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TechnicianDto> deleteTechnician(@PathVariable String id, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int technicianID = Integer.parseInt(id);
        int idCookie = Integer.parseInt(ID);
        if (idCookie == -1)
        {
            // TODO handle no login error with cookies (uncomment next line)
            //return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.UNAUTHORIZED);
            // Please login to delete a technician account.
        }
        Technician technician = technicianService.getTechnicianByID(technicianID);
        if (technician == null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.NOT_ACCEPTABLE);
            // Invalid technician. Please submit a valid technician account to be deleted.
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(idCookie)) && idCookie != technicianID) //does not have permission to edit.
        {
            // TODO handle bad login error with cookies (uncomment next line)
            //return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.UNAUTHORIZED);
            // You cannot delete a technician account other than your own.
        }
        return new ResponseEntity<>(convertToDto(technicianService.deleteTechnician(technician)), HttpStatus.OK);
    }

    @GetMapping(path = {"/viewschedule/{id}/{startDate}/{endDate}"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<TimeslotDto>> getAllByDate(@PathVariable("id") int technicianId, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate)//, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        Date newStartDate = Date.valueOf(startDate);
        Date newEndDate = Date.valueOf(endDate);

        if (technicianId == -1)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Technician technician = technicianService.getTechnicianByID(technicianId);
        if (technician == null)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Timeslot> timeslots = timeslotService.getTimeslotsByTechnicianBetweenDates(technician, newStartDate, newEndDate);
        return new ResponseEntity<>(convertToDto(timeslots), HttpStatus.OK);
    }

    @GetMapping(value = {"/getByID/{id}", "/getByID/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TechnicianDto> getByID(@PathVariable String id)
    {
        if (id == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        int ID = Integer.parseInt(id);
        Technician technician = technicianService.getTechnicianByID(ID);

        if (technician == null) return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(convertToDto(technician), HttpStatus.OK);
    }

    @GetMapping(value = {"/getByEmail/{email}", "/getByEmail/{email}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<TechnicianDto> getByEmail(@PathVariable String email)
    {
        if (email == null) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        Technician technician = technicianService.getTechnicianByEmail(email);

        if (technician == null) return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(convertToDto(technician), HttpStatus.OK);
    }

    @GetMapping(value = {"/getAll", "/getAll/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<TechnicianDto>> getAll()
    {
        ArrayList<Technician> technicians = new ArrayList<>(technicianService.getAllTechnicians());
        return new ResponseEntity<>(convertToDtoList(technicians), HttpStatus.OK);
    }
}
