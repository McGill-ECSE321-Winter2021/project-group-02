package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.SCRSUserRepository;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.SCRSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class SCRSUserService
{

    @Autowired
    SCRSUserRepository scrsUserRepository;

    @Transactional
    public List<SCRSUser> getAllSCRSUsers()
    {
        return toList(scrsUserRepository.findAll());
    }

    @Transactional
    public SCRSUser getSCRSUserByID(int id)
    {
        return scrsUserRepository.findByScrsUserId(id);
    }

    @Transactional
    public SCRSUser getSCRSUserByEmail(String email)
    {
        return scrsUserRepository.findByEmail(email);
    }

    @Transactional
    public SCRSUser getSCRSUserByName(String name)
    {
        return scrsUserRepository.findByName(name);
    }

    @Transactional
    public SCRSUser getSCRSUserByPhone(String phone)
    {
        return scrsUserRepository.findByPhone(phone);
    }
}
