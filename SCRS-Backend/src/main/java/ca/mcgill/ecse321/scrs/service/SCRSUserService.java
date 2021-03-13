package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.SCRSUserRepository;
import ca.mcgill.ecse321.scrs.dao.TechnicianRepository;
import ca.mcgill.ecse321.scrs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class SCRSUserService {

    @Autowired
    SCRSUserRepository scrsUserRepository;

    @Transactional
    public List<SCRSUser> getAllSCRSUsers() {
        return toList(scrsUserRepository.findAll());
    }
}
