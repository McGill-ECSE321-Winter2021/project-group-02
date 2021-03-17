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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTimeslotPersistence
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
     * Attribute test of the Timeslot class.
     *
     * @author Roey Borsteinas
     */
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
        assertEquals(ts.getWorkspace().getWorkspaceID(), actualTs.getWorkspace().getWorkspaceID());
    }

    /**
     * Association test of the Timeslot class.
     *
     * @author Roey Borsteinas
     */
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

        List<Timeslot> actualTimeslots = appointmentRepository.findByAppointmentID(appointment.getAppointmentID()).getTimeslots();

        assertEquals(1, actualTimeslots.size());
        Timeslot actualTimeslot = actualTimeslots.get(0);

        assertEquals(actualTimeslot.getTimeSlotID(), ts.getTimeSlotID());
        assertEquals(actualTimeslot.getWorkspace().getWorkspaceID(), ts.getWorkspace().getWorkspaceID());
    }

    /**
     * Association test of the Timeslot class.
     *
     * @author Roey Borsteinas
     */
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

    /**
     * Association test of the Timeslot class.
     *
     * @author Simon Nakane Marcil
     */
    @Test
    @Transactional
    public void testPersistAndLoadTimeslotByStartDateGreaterThanEqualAndStartDateLessThanEqual()
    {
        SCRS scrs = new SCRS();
        Workspace ws = new Workspace("test", scrs);
        Date day = new Date(2000, 2, 21);
        Date nextDay = new Date(2000, 2, 22);
        Date nextMonth = new Date(2000, 3, 21);
        Date previousDay = new Date(2000, 2, 20);
        Timeslot ts1 = new Timeslot(day, new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        Timeslot ts2 = new Timeslot(nextDay, new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        Timeslot ts3 = new Timeslot(previousDay, new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        Timeslot ts4 = new Timeslot(nextMonth, new Date(LocalDate.now().toEpochDay()), new Time(0), new Time(LocalDate.now().toEpochDay()), ws);
        scrsRepository.save(scrs);
        workspaceRepository.save(ws);
        timeslotRepository.save(ts2);
        timeslotRepository.save(ts3);
        timeslotRepository.save(ts1);
        timeslotRepository.save(ts4);

        List<Timeslot> actualTimeslots = timeslotRepository.findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDate(day, nextDay);

        assertEquals(2, actualTimeslots.size());
        Timeslot actualTimeslot = actualTimeslots.get(0);

        assertEquals(actualTimeslot.getTimeSlotID(), ts1.getTimeSlotID());
        assertEquals(actualTimeslot.getStartTime(), ts1.getStartTime());
    }
}
