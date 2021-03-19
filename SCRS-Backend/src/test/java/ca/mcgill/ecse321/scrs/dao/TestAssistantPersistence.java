package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Assistant;
import ca.mcgill.ecse321.scrs.model.SCRS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAssistantPersistence
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
     * Test the persistence of an assistant object in the database.
     * @author SimonNM
     */
    @Test
    @Transactional
    public void testPersistAndLoadAssistant()
    {
        SCRS scrs = new SCRS();
        Assistant assistant = new Assistant("name", "password", "name@mail.mcgill.ca", "111-1111", scrs);
        scrsRepository.save(scrs);
        assistantRepository.save(assistant);

        Assistant actualAssistant = assistantRepository.findByScrsUserId(assistant.getScrsUserId());

        assertNotNull(actualAssistant);
        assertEquals(assistant.getScrsUserId(), actualAssistant.getScrsUserId());
        assertEquals(assistant.getName(), actualAssistant.getName());
        assertEquals(assistant.getPassword(), actualAssistant.getPassword());
        assertEquals(assistant.getEmail(), actualAssistant.getEmail());
        assertEquals(assistant.getPhone(), actualAssistant.getPhone());
        assertEquals(scrs.getScrsId(), actualAssistant.getScrs().getScrsId());
    }

    /**
     * Test the persistence of an assistant object in the database.
     * @author SimonNM
     */
    @Test
    @Transactional
    public void testPersistAndLoadAssistantByEmail()
    {
        SCRS scrs = new SCRS();
        Assistant assistant = new Assistant("name", "password", "name@mail.mcgill.ca", "111-1111", scrs);
        scrsRepository.save(scrs);
        assistantRepository.save(assistant);

        Assistant actualAssistant = assistantRepository.findByEmail("aaaaaaaaaaaaa");

        assertNull(actualAssistant);
    }
}
