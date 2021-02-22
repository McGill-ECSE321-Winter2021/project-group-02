package ca.mcgill.ecse321.scrs.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.scrs.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestScrsPersistence {

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
    private  WorkspaceRepository workspaceRepository;

    @AfterEach
    public void clearDatabase() {
        appointmentRepository.deleteAll();
        timeslotRepository.deleteAll();
        assistantRepository.deleteAll();
        customerRepository.deleteAll();
        technicianRepository.deleteAll();
        scrsUserRepository.deleteAll();
        workspaceRepository.deleteAll();
    }

    //=========SIMON TESTS========== (Assistant, Technician tests)
    @Test
    public void testPersistAndLoadAssistant() {
        SCRS system = new SCRS();
        String name = "NotAdel";
        Assistant aAssistant = new Assistant(name, "password", "mail@notmail.mcgill.ca", "666 call devil", system);

        assistantRepository.save(aAssistant);

        aAssistant = null;

        aAssistant = assistantRepository.findByScrsUserId(1);
        assertNotNull(aAssistant);
        assertEquals(name, aAssistant.getName());
    }

    //=========ADEL TESTS========== (Customer tests)

    //=========ALIX TESTS========== (Appointment tests)

    //=========ROEY TESTS========== (Timeslot tests)

    //=========ALEXANDRA TESTS========== (Workspace tests)


}
