package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.dto.TechnicianDto;
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Technician;
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
    public ResponseEntity<TechnicianDto> createTechnician(@RequestBody Technician technician, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            //throw new IllegalArgumentException("Please login to create a technician account.");
        }
        if (technician == null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.EXPECTATION_FAILED);
            //throw new IllegalArgumentException("Invalid technician. Please submit a valid technician account to be created.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            //throw new IllegalArgumentException("You do not have permission to create a technician account.");
        }
        if (technicianService.getTechnicianByEmail(technician.getEmail()) != null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.ALREADY_REPORTED);
            //throw new IllegalArgumentException("Email already in use, please try a different email address.");
        }
        Technician newTechnician = technicianService.createTechnician(technician.getEmail(), technician.getName(), hash(technician.getPassword()), technician.getPhone());
        return new ResponseEntity<>(convertToDto(newTechnician), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<TechnicianDto> updateAssistant(@RequestBody Technician technician, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            //throw new IllegalArgumentException("Please login to modify a technician account.");
        }
        if (technician == null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.EXPECTATION_FAILED);
            //throw new IllegalArgumentException("Invalid technician.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != technician.getScrsUserId()) //does not have permission to edit.
        {
            //throw new IllegalArgumentException(".");
        }
        if (technicianService.getTechnicianByID(technician.getScrsUserId()) == null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.NOT_ACCEPTABLE);
            //throw new IllegalArgumentException("No such technician found.");
        }
        Technician updatedTechnician = technicianService.updateTechnicianInfo(technician);
        return new ResponseEntity<>(convertToDto(updatedTechnician), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<TechnicianDto> deleteTechnician(@RequestParam(value = "id") int technicianID, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            //throw new IllegalArgumentException("Please login to delete a technician account.");
        }
        Technician technician = technicianService.getTechnicianByID(technicianID);
        if (technician == null)
        {
            return new ResponseEntity<TechnicianDto>(new TechnicianDto(), HttpStatus.NOT_ACCEPTABLE);
            //throw new IllegalArgumentException("Invalid technician. Please submit a valid technician account to be deleted.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != technicianID) //does not have permission to edit.
        {
            //throw new IllegalArgumentException("You cannot delete a technician account other than your own.");
        }
        return new ResponseEntity<>(convertToDto(technicianService.deleteTechnician(technician)), HttpStatus.OK);
    }

    @GetMapping("/viewschedule/{id}")
    public ResponseEntity<ArrayList<TimeslotDto>> getAllByDate(@PathVariable("id") String technicianId, @RequestParam(name = "startDate") Date startDate, @RequestParam(name = "endDate") Date endDate, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        int technicianID = Integer.parseInt(technicianId);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to view the technician schedule.");
        }
        Technician technician = technicianService.getTechnicianByID(technicianID);
        if (technician == null)
        {
            throw new IllegalArgumentException("Invalid technician. Please submit a valid technician account to view the schedule.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != technicianID) //does not have permission to view.
        {
            throw new IllegalArgumentException("You cannot view a technician's schedule other than your own.");
        }
        List<TimeslotDto> timeslots = Helper.convertToDto((timeslotService.getTimeslotsByTechnicianBetweenDates(technician, startDate, endDate)));
        return new ResponseEntity<ArrayList<TimeslotDto>>(new ArrayList<>(timeslots), HttpStatus.OK);
    }
}
