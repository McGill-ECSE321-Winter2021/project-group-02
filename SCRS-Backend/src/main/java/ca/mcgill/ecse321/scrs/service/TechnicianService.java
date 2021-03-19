package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.TechnicianRepository;
import ca.mcgill.ecse321.scrs.model.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.checkAccountInfoValidity;
import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class TechnicianService
{

    @Autowired
    TechnicianRepository technicianRepository;

    @Transactional
    public Technician createTechnician(String email, String name, String password, String phone)
    {
        checkAccountInfoValidity(email, name, password, phone);
        Technician technician = new Technician(name, password, email, phone, null);
        technicianRepository.save(technician);
        return technician;
    }


    @Transactional
    public List<Technician> getAllTechnicians()
    {
        return toList(technicianRepository.findAll());
    }

    @Transactional
    public Technician getTechnicianByID(int id)
    {
        return technicianRepository.findByScrsUserId(id);
    }

    @Transactional
    public Technician getTechnicianByEmail(String email)
    {
        return technicianRepository.findByEmail(email);
    }

    @Transactional
    public Technician getTechnicianByName(String name)
    {
        return technicianRepository.findByName(name);
    }

    @Transactional
    public Technician getTechnicianByPhone(String phone)
    {
        return technicianRepository.findByPhone(phone);
    }

    @Transactional
    public Technician updateTechnicianInfo(Technician technician)
    {
        checkAccountInfoValidity(technician);
        technicianRepository.save(technician);
        return technician;
    }

    @Transactional
    public Technician deleteTechnician(Technician technician)
    {
        technicianRepository.delete(technician);
        return technician;
    }
}
