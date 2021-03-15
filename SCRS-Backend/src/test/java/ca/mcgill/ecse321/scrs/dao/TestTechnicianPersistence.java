package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.SCRS;
import ca.mcgill.ecse321.scrs.model.Technician;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTechnicianPersistence
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
     * Test the technician class
     * @author SimonNM
     */
    @Test
    @Transactional
    public void testPersistAndLoadTechnician()
    {
        SCRS scrs = new SCRS();
        Technician technician = new Technician("name", "password", "name@mail.mcgill.ca", "111-1111", scrs);

        scrsRepository.save(scrs);
        technicianRepository.save(technician);

        Technician actualTechnician = technicianRepository.findByScrsUserId(technician.getScrsUserId());

        assertNotNull(actualTechnician);
        assertEquals(technician.getScrsUserId(), actualTechnician.getScrsUserId());
        assertEquals(technician.getName(), actualTechnician.getName());
        assertEquals(technician.getPassword(), actualTechnician.getPassword());
        assertEquals(technician.getEmail(), actualTechnician.getEmail());
        assertEquals(technician.getPhone(), actualTechnician.getPhone());
        assertEquals(scrs.getScrsId(), actualTechnician.getScrs().getScrsId());
    }
}
