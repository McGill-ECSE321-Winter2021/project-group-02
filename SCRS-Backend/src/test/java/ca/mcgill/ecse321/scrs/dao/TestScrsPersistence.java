package ca.mcgill.ecse321.scrs.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.scrs.model.*;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;
import org.springframework.transaction.annotation.Transactional;

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


    //=========SIMON TESTS========== (Assistant and Technician tests)
    @Test
    @Transactional
    public void testPersistAndLoadAssistant()
    {
        SCRS system = new SCRS();
        String name = "NotAdel";
        Assistant aAssistant = new Assistant(name, "password", "mail@notmail.mcgill.ca", "666 call devil", system);

        scrsRepository.save(system);
        assistantRepository.save(aAssistant);

        aAssistant = null;

        aAssistant = assistantRepository.findByScrsUserId(1);
        assertNotNull(aAssistant);
        assertEquals(name, aAssistant.getName());
    }

    //=========ADEL TESTS========== (Customer and SCRS tests)

    //@Test
    //@Transactional
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
        assertEquals(scrs.getWorkspaces().size(), actualScrs.getWorkspaces().size());
        assertEquals(scrs.getWorkspace(0), space); //test if workspace association was properly stored/read
    }

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
        Timeslot ts = new Timeslot(new Date(0),new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        Appointment app = new Appointment(AppointmentType.CarWash, "service", "note", 5, "feedback", true, customer, scrs, ts);
        customer.addAppointment(app);

        scrsRepository.save(scrs);
        workspaceRepository.save(ws);
        timeslotRepository.save(ts);
        appointmentRepository.save(app);

        //save customer
        customerRepository.save(customer);

        //check test outputs
        Customer actualCustomer = customerRepository.findByScrsUserId(customer.getScrsUserId());
        assertNotNull(customer);
        assertEquals(customer.getName(), customer.getName());
        assertNotNull(customer.getAppointment(0));
        assertEquals(app, customer.getAppointment(0));
    }

    //=========ALIX TESTS========== (Appointment tests)
    @Test
    @Transactional
    public void testPersistAndLoadAppointment()
    {
        // creating objects
        SCRS system = new SCRS();
        Customer customer = new Customer("Rick Roll", "You just got Rick Rolled", "Ha Gottem@gmail.com", "(666) 666-6666", system);
        Workspace workspace = new Workspace("mom get out of my room I'm playing Minecraft", system);
        Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
        Appointment appointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good",90, "boop", false, customer, system, timeslot );

        //saving them
        scrsRepository.save(system);
        customerRepository.save(customer);
        workspaceRepository.save(workspace);
        timeslotRepository.save(timeslot);
        Appointment appointment2 = appointmentRepository.save(appointment);     //for getting the ID

        //check appointment
        System.out.println(appointment2.getAppointmentID());
        Appointment appointment1 = appointmentRepository.findByAppointmentID(appointment2.getAppointmentID());
        assertNotNull(appointment);
        assertEquals(appointment1.getFeedback(), appointment.getFeedback());

        //check appointment-timeslot relation
        Timeslot timeslot1 = appointment.getTimeslot(0);
        assertNotNull(timeslot1);
        assertEquals(timeslot1.getEndTime(), timeslot.getEndTime());

        //check appointment-customer relation
        Customer customer1 = appointment.getCustomer();
        assertNotNull(customer1);
        assertEquals(customer1.getName(), customer.getName());

    }

    //=========ROEY TESTS========== (Timeslot tests)
    @Test
    @Transactional
    public void testPersistAndLoadTimeslotByID()
    {
        SCRS scrs = new SCRS();
        Workspace ws = new Workspace("test", scrs);
        Timeslot ts = new Timeslot(new Date(0),new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        scrsRepository.save(scrs);
        workspaceRepository.save(ws);
        timeslotRepository.save(ts);

        Timeslot actualTs = timeslotRepository.findByTimeSlotID(ts.getTimeSlotID());

        assertNotNull(actualTs);
        assertEquals(ts.getStartDate().toString(), actualTs.getStartDate().toString());
        assertEquals(ts.getEndDate().toString(), actualTs.getEndDate().toString());
        assertEquals(ts.getStartTime().toString(), actualTs.getStartTime().toString());
        assertEquals(ts.getEndTime().toString(), actualTs.getEndTime().toString());

        List<Technician> expectedTechs = ts.getTechnicians();
        List<Technician> actualTechs = actualTs.getTechnicians();
        assertEquals(expectedTechs.size(), actualTechs.size());
        for (Technician actualTech : actualTechs)
        {
            assertTrue(expectedTechs.contains(actualTech));
        }
        assertEquals(ts.getAppointment(), actualTs.getAppointment());
        assertEquals(ts.getWorkspace().getWorkspaceID(), actualTs.getWorkspace().getWorkspaceID());
    }

    //=========ALEXANDRA TESTS========== (Workspace tests)


}
