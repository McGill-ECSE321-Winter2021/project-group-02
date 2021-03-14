package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.TechnicianDto;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/technician")
public class TechnicianController
{
    @Autowired
    TechnicianService technicianService;

    @PostMapping(value = {"/create", "/create/"})
    public TechnicianDto createAssistant(@RequestBody Technician technician)
    {
        return convertToDTO(technicianService.createTechnician(technician.getEmail(), technician.getName(), technician.getPassword(), technician.getPhone()));
    }

    // ================= Private Helpers ================

    private TechnicianDto convertToDTO(Technician t)
    {
        if (t == null) throw new IllegalArgumentException("There is no such assistant!");
        return new TechnicianDto(t.getScrsUserId(), t.getName(), t.getEmail(), t.getPhone());
    }
}
