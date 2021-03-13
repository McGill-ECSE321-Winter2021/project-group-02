package ca.mcgill.ecse321.scrs.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.scrs.dao.*;
import ca.mcgill.ecse321.scrs.model.*;
import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class SCRSService {

    @Autowired
    SCRSRepository scrsRepository;

}