package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.TechnicianRepository;
import ca.mcgill.ecse321.scrs.model.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class TechnicianService {

    @Autowired
    TechnicianRepository technicianRepository;

    @Transactional
    public Technician createTechnician(String email, String name, String password, String phone) {
        Technician technician = new Technician();
        technician.setEmail(email);
        technician.setName(name);
        technician.setPassword(password);
        technician.setPhone(phone);
        technicianRepository.save(technician);
        return technician;
    }

    @Transactional
    public List<Technician> getAllTechnicians() {
        return toList(technicianRepository.findAll());
    }

    @Transactional
    public Technician getTechnicianByID(int id) {
        return technicianRepository.findByScrsUserId(id);
    }

    @Transactional
    public Technician getTechnicianByEmail(String email) {
        return technicianRepository.findByEmail(email);
    }

    @Transactional
    public Technician getTechnicianByName(String name) {
        return technicianRepository.findByName(name);
    }

    @Transactional
    public Technician getTechnicianByPhone(String phone) {
        return technicianRepository.findByPhone(phone);
    }

    @Transactional
    public Technician updateTechnicianInfo(Technician technician)
    {
        technicianRepository.save(technician);
        return technician;
    }
}
