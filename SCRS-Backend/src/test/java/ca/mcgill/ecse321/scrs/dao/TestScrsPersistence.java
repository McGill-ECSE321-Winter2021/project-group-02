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
    }


    //=========SIMON TESTS========== (Assistant and Technician tests)
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

    //=========ADEL TESTS========== (Customer and SCRS tests)

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
        assertNotNull(actualCustomer);
        assertEquals(customer.getName(), actualCustomer.getName());
        assertEquals(customer.getPassword(), actualCustomer.getPassword());
        assertEquals(customer.getEmail(), actualCustomer.getEmail());
        assertEquals(customer.getPhone(), actualCustomer.getPhone());
        assertNotNull(actualCustomer.getAppointment(0));
        assertEquals(customer.getAppointment(0), actualCustomer.getAppointment(0));
    }

    //=========ALIX TESTS========== (Appointment tests)
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
            Appointment differentAppointment = new Appointment(AppointmentType.Checkup, "beep", "shrimp was good",90, "boop", false, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(differentAppointment);
        }

        //check appointment
        List<Appointment> appointment1 = appointmentRepository.findAppointmentsByAppointmentType(AppointmentType.CarWash);
        assertNotNull(appointment1);
        assertEquals(appointment1.size(), 5);
    }

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
            Appointment differentAppointment = new Appointment(AppointmentType.CarWash, "beep", "shrimp was good",90, "boop", false, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(differentAppointment);
        }

        //check appointment
        List<Appointment> appointment1 = appointmentRepository.findAppointmentsByPaid(true);
        assertNotNull(appointment1);
        assertEquals(appointment1.size(), 5);
    }

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
            Appointment differentAppointment = new Appointment(AppointmentType.CarWash, "beep", "shrimp was good",90, "boop", false, customer, system, timeslot );
            timeslotRepository.save(timeslot);
            appointmentRepository.save(differentAppointment);
        }

        //check appointment
        List<Appointment> appointment1 = appointmentRepository.findAppointmentsByCustomer(customer);
        assertNotNull(appointment1);
        assertEquals(appointment1.size(), 8);
    }


    //=========ROEY TESTS========== (Timeslot tests)
    @Test
    @Transactional
    public void testPersistAndLoadTimeslotByID()
    {
        SCRS scrs = new SCRS();
        Workspace ws = new Workspace("test", scrs);
        Timeslot ts = new Timeslot(new Date(0), new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
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

    @Test
    @Transactional
    public void testPersistAndLoadTimeslotByAppointment()
    {
        SCRS scrs = new SCRS();
        Workspace ws = new Workspace("test", scrs);
        Timeslot ts = new Timeslot(new Date(0), new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        Customer customer = new Customer("Rick Roll", "You just got Rick Rolled", "Ha Gottem@gmail.com", "(666) 666-6666", scrs);
        Appointment appointment = new Appointment(Appointment.AppointmentType.CarWash, "beep", "shrimp was good", 90, "boop", false, customer, scrs, ts);
        scrsRepository.save(scrs);
        workspaceRepository.save(ws);
        timeslotRepository.save(ts);
        customerRepository.save(customer);
        appointmentRepository.save(appointment);

        List<Timeslot> actualTimeslots = timeslotRepository.findByAppointment(appointment);

        assertEquals(1, actualTimeslots.size());
        Timeslot actualTimeslot = actualTimeslots.get(0);

        assertEquals(actualTimeslot.getTimeSlotID(), ts.getTimeSlotID());
        assertEquals(actualTimeslot.getWorkspace().getWorkspaceID(), ts.getWorkspace().getWorkspaceID());
    }

    @Test
    @Transactional
    public void testPersistAndLoadTimeslotByWorkspace()
    {
        SCRS scrs = new SCRS();
        Workspace ws = new Workspace("test", scrs);
        Timeslot ts = new Timeslot(new Date(0), new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        scrsRepository.save(scrs);
        workspaceRepository.save(ws);
        timeslotRepository.save(ts);

        List<Timeslot> actualTimeslots = timeslotRepository.findByWorkspace(ws);

        assertEquals(1, actualTimeslots.size());
        Timeslot actualTimeslot = actualTimeslots.get(0);

        assertEquals(actualTimeslot.getTimeSlotID(), ts.getTimeSlotID());
        assertEquals(actualTimeslot.getWorkspace().getWorkspaceID(), ts.getWorkspace().getWorkspaceID());
    }

    @Test
    @Transactional
    public void testPersistAndLoadTimeslotByTechnicians()
    {
        SCRS scrs = new SCRS();
        Workspace ws = new Workspace("test", scrs);
        Timeslot ts = new Timeslot(new Date(0), new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        Technician tech = new Technician("SomeTech", "password", "email", "phone", scrs);
        scrsRepository.save(scrs);
        workspaceRepository.save(ws);
        timeslotRepository.save(ts);
        technicianRepository.save(tech);

        List<Timeslot> actualTimeslots = timeslotRepository.findByTechnicians(tech);

        assertEquals(0, actualTimeslots.size());

        tech.addAvailability(ts);
        technicianRepository.save(tech);

        actualTimeslots = timeslotRepository.findByTechnicians(tech);
        assertNotEquals(0, actualTimeslots.size());
        Timeslot actualTimeslot = actualTimeslots.get(0);

        assertEquals(actualTimeslot.getTimeSlotID(), ts.getTimeSlotID());
        assertEquals(actualTimeslot.getWorkspace().getWorkspaceID(), ts.getWorkspace().getWorkspaceID());
    }

    //=========ALEXANDRA TESTS========== (Workspace tests)
    @Test
    @Transactional
    public void testPersistAndLoadWorkspace()
    {
        SCRS scrs = new SCRS();
        Workspace workspace = new Workspace("test", scrs);
        Timeslot timeslot = new Timeslot(new Date(0),new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), workspace);
        
        scrsRepository.save(scrs);
        workspaceRepository.save(workspace);
        timeslotRepository.save(timeslot);
        
        Workspace actualWorkspace = workspaceRepository.findByWorkspaceID(workspace.getWorkspaceID());
        assertNotNull(actualWorkspace);
        assertEquals(workspace.getSpaceType(), actualWorkspace.getSpaceType());
        assertNotNull(actualWorkspace.getAvailabilities().get(0));
        assertEquals(workspace.getAvailabilities().get(0), actualWorkspace.getAvailabilities().get(0));
    }
    
}
