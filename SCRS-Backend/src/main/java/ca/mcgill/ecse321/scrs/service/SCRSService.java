package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.SCRSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SCRSService
{

    @Autowired
    SCRSRepository scrsRepository;

    //No tests  since there are no service methods in the SCRSService class

}