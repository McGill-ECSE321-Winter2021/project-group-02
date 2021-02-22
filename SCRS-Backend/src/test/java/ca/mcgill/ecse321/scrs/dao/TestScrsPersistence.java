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
