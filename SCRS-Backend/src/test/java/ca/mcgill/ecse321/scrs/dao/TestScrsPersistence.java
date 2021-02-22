package ca.mcgill.ecse321.scrs.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.scrs.model.*;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;

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

    //=========ADEL TESTS========== (Customer and SCRS tests)

    @Test
    public void testPersistAndLoadSCRS() {
        //create scrs
        int id = 1;
        SCRS scrs = new SCRS();
        scrs.setScrsId(1);

        //create and add test workspace to scrs
        Workspace space = new Workspace("test", scrs);
        workspaceRepository.save(space);
        scrs.addWorkspace(space);

        //save scrs
        scrsRepository.save(scrs);

        scrs = null;

        //check test outputs
        scrs = scrsRepository.findByScrsId(id);
        assertNotNull(scrs);
        assertEquals(scrs.getScrsId(), id); //test if ID was properly stored/read
        assertNotNull(scrs.getWorkspace(0));
        assertEquals(scrs.getWorkspace(0), space); //test if workspace association was properly stored/read
    }

    @Test
    public void testPersistAndLoadCustomer() {
        //create dummy scrs
        SCRS scrs = new SCRS();

        //create customer with data
        int id = 32;
        String name = "testName";
        Customer customer = new Customer(name, "password", "email", "phone", scrs, id);

        //create appointment -> timeslot -> workspace to test the association
        Workspace ws = new Workspace("test", scrs);
        Timeslot ts = new Timeslot(new Date(0),new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        Appointment app = new Appointment(AppointmentType.CarWash, "service", "note", 5, "feedback", true, customer, scrs, ts);
        customer.addAppointment(app);
        workspaceRepository.save(ws);
        timeslotRepository.save(ts);
        appointmentRepository.save(app);

        //save customer
        customerRepository.save(customer);

        customer = null;

        //check test outputs
        customer = customerRepository.findByScrsUserId(id);
        assertNotNull(customer);
        assertEquals(customer.getName(), name);
        assertNotNull(customer.getAppointment(0));
        assertEquals(customer.getAppointment(0), app);
    }

    //=========ALIX TESTS========== (Appointment tests)
    @Test
    public void testPersistAndLoadAppointment() {

        // creating objects
        SCRS system = new SCRS();
        Customer customer = new Customer("Rick Roll", "You just got Rick Rolled", "Ha Gottem@gmail.com", "(666) 666-6666", system);
        Workspace workspace = new Workspace("mom get out of my room I'm playing Minecraft", system);
        Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
        String aFeedback = "boop";
        Appointment appointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good",90, aFeedback, false, customer, system, timeslot );

        //saving them
        customerRepository.save(customer);
        workspaceRepository.save(workspace);
        timeslotRepository.save(timeslot);
        appointmentRepository.save(appointment);

        appointment = null;

        //check appointment
        appointment = appointmentRepository.findByAppointmentID(1);
        assertNotNull(appointment);
        assertEquals(aFeedback, appointment.getFeedback());

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

    //=========ALEXANDRA TESTS========== (Workspace tests)


}
