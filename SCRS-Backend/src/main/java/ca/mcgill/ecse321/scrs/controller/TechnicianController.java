package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.CustomerDto;
import ca.mcgill.ecse321.scrs.dto.TechnicianDto;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ca.mcgill.ecse321.scrs.controller.Helper.convertToDTO;

@RestController
@RequestMapping(path = "/api/technician")
public class TechnicianController
{
    @Autowired
    TechnicianService technicianService;

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<TechnicianDto> createTechnician(@RequestBody Technician technician)
    {
        if (technician == null)
        {
            throw new IllegalArgumentException("Invalid technician. Please submit a valid technician account to be created.");
        }
        if ( technicianService.getTechnicianByEmail(technician.getEmail()) != null)
        {
            throw new IllegalArgumentException("Email already in use, please try a different email address.");
        }
        return new ResponseEntity<TechnicianDto>(convertToDTO(technicianService.createTechnician(technician.getEmail(), technician.getName(), technician.getPassword(), technician.getPhone())), HttpStatus.OK);
    }
}
