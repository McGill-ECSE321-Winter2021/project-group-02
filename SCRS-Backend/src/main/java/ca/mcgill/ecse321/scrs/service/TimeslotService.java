package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class TimeslotService {

    @Autowired
    TimeslotRepository timeslotRepository;

    @Transactional
    public List<Timeslot> getAllTimeslots() {
        return toList(timeslotRepository.findAll());
    }
}
