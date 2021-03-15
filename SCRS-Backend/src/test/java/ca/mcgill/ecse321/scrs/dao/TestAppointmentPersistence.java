package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAppointmentPersistence
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
     * Appointment class ID test
     * @author Alix R-L
     */
    @Test
    @Transactional
    public void testPersistAndLoadAppointmentByID()
    {
        // creating objects
        SCRS system = new SCRS();
        Customer customer = new Customer("Rick Roll", "You just got Rick Rolled", "Ha Gottem@gmail.com", "(666) 666-6666", system);
        Workspace workspace = new Workspace("mom get out of my room I'm playing Minecraft", system);
        Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
        Appointment appointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good", 90, "boop", false, customer, system, timeslot);

        //saving them
        scrsRepository.save(system);
        customerRepository.save(customer);
        workspaceRepository.save(workspace);
        timeslotRepository.save(timeslot);
        Appointment appointment2 = appointmentRepository.save(appointment);     //for getting the ID

        //check appointment
        Appointment appointment1 = appointmentRepository.findByAppointmentID(appointment2.getAppointmentID());
        assertNotNull(appointment1);
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

    /**
     * Association test of the Appointment class
     * @author Alix R-L
     */
    @Test
    @Transactional
    public void testPersistAndLoadAppointmentByAppointmentType()
    {
        // creating objects
        SCRS system = new SCRS();
        Customer customer = new Customer("Rick Roll", "You just got Rick Rolled", "Ha Gottem@gmail.com", "(666) 666-6666", system);
        Workspace workspace = new Workspace("mom get out of my room I'm playing Minecraft", system);

        //saving them
        scrsRepository.save(system);
        customerRepository.save(customer);
        workspaceRepository.save(workspace);
        for(int i = 0 ; i < 5 ; i++){
            Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
            Appointment appointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good",90, "boop", false, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(appointment);
        }
        for(int i = 0 ; i < 3 ; i++){
            Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
            Appointment differentAppointment = new Appointment(Appointment.AppointmentType.Checkup, "beep", "shrimp was good",90, "boop", false, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(differentAppointment);
        }

        //check appointment
        List<Appointment> appointment1 = appointmentRepository.findAppointmentsByAppointmentType(Appointment.AppointmentType.CarWash);
        assertNotNull(appointment1);
        assertEquals(appointment1.size(), 5);
    }

    /**
     * Attribute test of the Appointment class
     * @author Alix R-L
     */
    @Test
    @Transactional
    public void testPersistAndLoadAppointmentByPaid()
    {
        // creating objects
        SCRS system = new SCRS();
        Customer customer = new Customer("Rick Roll", "You just got Rick Rolled", "Ha Gottem@gmail.com", "(666) 666-6666", system);
        Workspace workspace = new Workspace("mom get out of my room I'm playing Minecraft", system);

        //saving them
        scrsRepository.save(system);
        customerRepository.save(customer);
        workspaceRepository.save(workspace);
        for(int i = 0 ; i < 5 ; i++){
            Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
            Appointment appointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good",90, "boop", true, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(appointment);
        }
        for(int i = 0 ; i < 3 ; i++){
            Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
            Appointment differentAppointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good",90, "boop", false, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(differentAppointment);
        }

        //check appointment
        List<Appointment> appointment1 = appointmentRepository.findAppointmentsByPaid(true);
        assertNotNull(appointment1);
        assertEquals(appointment1.size(), 5);
    }

    /**
     * Association test of the Appointment class
     * @author Alix R-L
     */
    @Test
    @Transactional
    public void testPersistAndLoadAppointmentByCustomer()
    {
        // creating objects
        SCRS system = new SCRS();
        Customer customer = new Customer("Rick Roll", "You just got Rick Rolled", "Ha Gottem@gmail.com", "(666) 666-6666", system);
        Workspace workspace = new Workspace("mom get out of my room I'm playing Minecraft", system);

        //saving them
        scrsRepository.save(system);
        customerRepository.save(customer);
        workspaceRepository.save(workspace);
        for(int i = 0 ; i < 5 ; i++){
            Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
            Appointment appointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good",90, "boop", true, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(appointment);
        }
        for(int i = 0 ; i < 3 ; i++){
            Timeslot timeslot = new Timeslot(new Date(LocalDate.now().toEpochDay()), new Date(LocalDate.now().toEpochDay()), new Time(3333), new Time(6666), workspace);
            Appointment differentAppointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good",90, "boop", false, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(differentAppointment);
        }

        //check appointment
        List<Appointment> appointment1 = appointmentRepository.findAppointmentsByCustomer(customer);
        assertNotNull(appointment1);
        assertEquals(appointment1.size(), 8);
    }
}
