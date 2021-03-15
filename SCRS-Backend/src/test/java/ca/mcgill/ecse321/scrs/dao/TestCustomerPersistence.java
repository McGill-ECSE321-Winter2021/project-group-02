package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Customer;
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
public class TestCustomerPersistence
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
     * Test the persistence of the customer class.
     * @author Adel Ahram
     */
    @Test
    @Transactional
    public void testPersistAndLoadCustomer()
    {
        //create dummy scrs
        SCRS scrs = new SCRS();

        //create customer with data
        Customer customer = new Customer("name", "password", "email", "phone", scrs);

        //create appointment -> timeslot -> workspace to test the association
        Workspace ws = new Workspace("test", scrs);
        Timeslot ts = new Timeslot(new Date(0), new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);

        scrsRepository.save(scrs);
        workspaceRepository.save(ws);
        timeslotRepository.save(ts);

        //save customer
        customerRepository.save(customer);

        //check test outputs
        Customer actualCustomer = customerRepository.findByScrsUserId(customer.getScrsUserId());
        assertNotNull(actualCustomer);
        assertEquals(customer.getName(), actualCustomer.getName());
        assertEquals(customer.getPassword(), actualCustomer.getPassword());
        assertEquals(customer.getEmail(), actualCustomer.getEmail());
        assertEquals(customer.getPhone(), actualCustomer.getPhone());
    }
}
