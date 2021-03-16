package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.SCRS;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestWorkspacePersistence {
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
    public void clearDatabase() {
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
     * Persistence test of the Workspace class.
     * 
     * @author Alexandra Gafencu
     */
    @Test
    @Transactional
    public void testPersistAndLoadWorkspace() {
        SCRS scrs = new SCRS();
        Workspace workspace = new Workspace("test", scrs);
        Timeslot timeslot = new Timeslot(new Date(0), new Date(LocalDate.now().toEpochDay()), new Time(0),
                new Time(LocalDate.now().toEpochDay()), workspace);

        scrsRepository.save(scrs);
        workspaceRepository.save(workspace);
        timeslotRepository.save(timeslot);

        Workspace actualWorkspace = workspaceRepository.findByWorkspaceID(workspace.getWorkspaceID());
        assertNotNull(actualWorkspace);
        assertEquals(workspace.getSpaceName(), actualWorkspace.getSpaceName());
        assertNotNull(actualWorkspace.getAvailabilities().get(0));
        assertEquals(workspace.getAvailabilities().get(0), actualWorkspace.getAvailabilities().get(0));
    }
}
