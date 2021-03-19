package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.*;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestScrsPersistence
{
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SCRSRepository scrsRepository;
    @Autowired
    private SCRSUserRepository scrsUserRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @AfterEach
    public void clearDatabase()
    {
        appointmentRepository.deleteAll();
        timeslotRepository.deleteAll();
        assistantRepository.deleteAll();
        customerRepository.deleteAll();
        technicianRepository.deleteAll();
        scrsUserRepository.deleteAll();
        workspaceRepository.deleteAll();
        scrsRepository.deleteAll();
    }

    /**
     * Test the persistence of the SCRS class
     * @author Adel Ahram
     */
    @Test
    @Transactional
    public void testPersistAndLoadSCRS()
    {
        //create scrs
        SCRS scrs = new SCRS();
        //create and add test workspace to scrs
        Workspace space = new Workspace("test", scrs);
        scrs.addWorkspace(space);

        //save scrs
        scrsRepository.save(scrs);
        workspaceRepository.save(space);

        //check test outputs
        SCRS actualScrs = scrsRepository.findByScrsId(scrs.getScrsId());
        assertNotNull(actualScrs);
        assertEquals(scrs.getScrsId(), actualScrs.getScrsId()); //test if ID was properly stored/read
        assertEquals(scrs.getWorkspace(0), space); //test if workspace association was properly stored/read
    }

}
