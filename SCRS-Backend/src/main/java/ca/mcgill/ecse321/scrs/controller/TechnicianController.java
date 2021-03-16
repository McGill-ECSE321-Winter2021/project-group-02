package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.dto.TechnicianDto;
<<<<<<< Updated upstream
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
=======
import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
>>>>>>> Stashed changes
import ca.mcgill.ecse321.scrs.service.TechnicianService;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
<<<<<<< Updated upstream

import static ca.mcgill.ecse321.scrs.controller.Helper.*;
=======

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDTO;
import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDto;
>>>>>>> Stashed changes

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
            throw new IllegalArgumentException("Please login to create a technician account.");
        }
        if (technician == null)
        {
            throw new IllegalArgumentException("Invalid technician. Please submit a valid technician account to be created.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You do not have permission to create a technician account.");
        }
        if ( technicianService.getTechnicianByEmail(technician.getEmail()) != null)
        {
            throw new IllegalArgumentException("Email already in use, please try a different email address.");
        }
        return new ResponseEntity<TechnicianDto>(convertToDTO(technicianService.createTechnician(technician.getEmail(), technician.getName(), hash(technician.getPassword()), technician.getPhone())), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<TechnicianDto> updateAssistant(@RequestBody Technician technician, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to modify a technician account.");
        }
        if (technician == null)
        {
            throw new IllegalArgumentException("Invalid technician.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != technician.getScrsUserId()) //does not have permission to edit.
        {
            throw new IllegalArgumentException(".");
        }
        if (technicianService.getTechnicianByID(technician.getScrsUserId()) == null)
        {
            throw new IllegalArgumentException("No such technician found.");
        }
        return new ResponseEntity<TechnicianDto>(convertToDTO(technicianService.updateTechnicianInfo(technician)), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<TechnicianDto> deleteTechnician(@RequestParam(value = "id") int technicianID, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to delete a technician account.");
        }
        Technician technician = technicianService.getTechnicianByID(technicianID);
        if (technician == null)
        {
            throw new IllegalArgumentException("Invalid technician. Please submit a valid technician account to be deleted.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id)) && id != technicianID) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You cannot delete a technician account other than your own.");
        }
        return new ResponseEntity<TechnicianDto>(convertToDTO(technicianService.deleteTechnician(technician)), HttpStatus.OK);
    }

    //List<TimeslotDto> convertToDto

    @GetMapping("/viewtechnicianschedule/{id}")
    public ResponseEntity<ArrayList<TimeslotDto>> getAll(@PathVariable("id") String id) {
        int ID = Integer.parseInt(id);
        Technician technician=technicianService.getTechnicianById(ID);
        List<TimeslotDto> timeslots=Helper.convertToDto((timeslotService.getTimeslotsByTechnician(technician)));

        return new ResponseEntity<ArrayList<TimeslotDto>>(new ArrayList<>(timeslots), HttpStatus.OK);
    }


}
