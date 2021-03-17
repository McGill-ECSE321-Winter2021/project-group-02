package ca.mcgill.ecse321.scrs.service;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.err;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import ca.mcgill.ecse321.scrs.dao.AppointmentRepository;
import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class TestAppointmentService
{

    @Mock
    private AppointmentRepository appointmentDao;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private TimeslotRepository timeslotRepository;

    @InjectMocks
    private AppointmentService service;

    private static final int testID = 1;
    private static final int wrongID = 420;
    private static final Time testTime = new Time(currentTimeMillis());
    private static final Date testDate = new Date(currentTimeMillis());
    private static final AppointmentType testType = AppointmentType.Maintenance;
    private static final Customer testCustomer = new Customer();
    private static final Timeslot testTimeslot = new Timeslot();
    private static final ArrayList<Timeslot> timeslots = new ArrayList<Timeslot>();

    @BeforeAll
    public static void instantiateVars()
    {
        testCustomer.setScrsUserId(testID);
        testCustomer.setEmail("email");
        testCustomer.setName("name");
        testCustomer.setPassword("password");
        testCustomer.setPhone("phone");
        testTimeslot.setStartTime(testTime);
        testTimeslot.setEndTime(testTime);
        testTimeslot.setStartDate(testDate);
        testTimeslot.setEndDate(testDate);
        testTimeslot.setTimeSlotID(testID);
        timeslots.add(testTimeslot);
    }


    @BeforeEach
    public void setMockOutput()
    {
        lenient().when(appointmentDao.findByAppointmentID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testID))
            {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(testID);
                appointment.setAppointmentType(testType);
                appointment.setPaid(false);
                appointment.setTimeslots(timeslots.toArray(new Timeslot[0]));
                appointment.setCustomer(testCustomer);
                return appointment;
            } else
            {
                return null;
            }
        });
        lenient().when(appointmentDao.findAppointmentsByCustomer(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testCustomer))
            {
                ArrayList<Appointment> tempList = new ArrayList<>();
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(testID);
                appointment.setAppointmentType(testType);
                appointment.setPaid(false);
                appointment.setTimeslots(timeslots.toArray(new Timeslot[0]));
                appointment.setCustomer(testCustomer);
                tempList.add(appointment);
                return tempList;
            } else
            {
                return null;
            }
        });
        lenient().when(appointmentDao.findAppointmentsByTimeslots(any(Timeslot.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testTimeslot))
            {
                ArrayList<Appointment> tempList = new ArrayList<>();
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(testID);
                appointment.setAppointmentType(testType);
                appointment.setPaid(false);
                appointment.setTimeslots(timeslots.toArray(new Timeslot[0]));
                appointment.setCustomer(testCustomer);
                tempList.add(appointment);
                return tempList;
            } else
            {
                return null;
            }
        });
        lenient().when(appointmentDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
                ArrayList<Appointment> tempList = new ArrayList<>();
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(testID);
                appointment.setAppointmentType(testType);
                appointment.setPaid(false);
                appointment.setTimeslots(timeslots.toArray(new Timeslot[0]));
                appointment.setCustomer(testCustomer);
                tempList.add(appointment);
                return tempList;
        });
        lenient().when(customerRepository.findByScrsUserId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(testCustomer.getScrsUserId()))
            {
                return testCustomer;
            } else
            {
                return null;
            }
        });
        lenient().when(timeslotRepository.findByTimeSlotID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(testTimeslot.getTimeSlotID()))
            {
                return testTimeslot;
            } else
            {
                return null;
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(appointmentDao.save(any(Appointment.class))).thenAnswer(returnParameterAsAnswer);
    }

    //TODO Populate all the empty tests

    @Test
    public void testCreate()
    {
        Appointment appointment = null;
        try
        {
            appointment = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            appointment.setAppointmentID(testID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(appointment);
        assertEquals(testCustomer.getScrsUserId(), appointment.getCustomer().getScrsUserId());
        assertEquals(testType, appointment.getAppointmentType());
        assertEquals(testTimeslot.getTimeSlotID(), appointment.getTimeslot(0).getTimeSlotID());
    }

    @Test
    public void testCreateNoType()
    {
        String error = null;
        Appointment appointment = null;
        try
        {
            appointment = service.createAppointment(null, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals("Please submit a valid appointment type.", error);
    }

    @Test
    public void testCreateNullTimeslots()
    {
        String error = null;
        Appointment appointment = null;
        try
        {
            appointment = service.createAppointment(testType, null, null, false, testCustomer, (Timeslot) null);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals("Please select at least one valid timeslot.", error);
    }

    @Test
    public void testCreateEmptyTimeslots()
    {
        String error = null;
        Appointment appointment = null;
        try
        {
            appointment = service.createAppointment(testType, null, null, false, testCustomer);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals("Please select at least one valid timeslot.", error);
    }

    @Test
    public void testCreateNoCustomer()
    {
        String error = null;
        Appointment appointment = null;
        try
        {
            appointment = service.createAppointment(testType, null, null, false, null, timeslots.toArray(new Timeslot[0]));

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals("Please submit a valid customer.", error);
    }

    @Test
    public void testGetAll()
    {
        ArrayList<Appointment> appointments = null;
        try
        {
            appointments = new ArrayList<>(service.getAllAppointments());

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(appointments);
        assertNotEquals(0, appointments.size());
        assertEquals(testCustomer.getScrsUserId(), appointments.get(0).getCustomer().getScrsUserId());
        assertEquals(testType, appointments.get(0).getAppointmentType());
        assertEquals(testTimeslot.getTimeSlotID(), appointments.get(0).getTimeslot(0).getTimeSlotID());
    }

    @Test
    public void testGetByID()
    {
        Appointment appointment = null;
        try
        {
            appointment = service.getAppointmentById(testID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(appointment);
        assertEquals(testID, appointment.getAppointmentID());
        assertEquals(testCustomer, appointment.getCustomer());
        assertEquals(timeslots, appointment.getTimeslots());
        assertEquals(testType, appointment.getAppointmentType());
    }

    @Test
    public void testGetByWrongID()
    {
        Appointment appointment = null;
        try
        {
            appointment = service.getAppointmentById(wrongID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(appointment);

    }

    @Test
    public void testGetByCustomer()
    {
        
    }

    @Test
    public void testGetByCustomerNull()
    {

    }

    @Test
    public void testGetByWrongCustomer()
    {

    }

    @Test
    public void testGetByTimeslot()
    {

    }

    @Test
    public void testGetByTimeslotNull()
    {

    }

    @Test
    public void testGetByWrongTimeslot()
    {

    }

    @Test
    public void testRate()
    {

    }

    @Test
    public void testRateInvalidID()
    {

    }

    @Test
    public void testRateInvalidRating()
    {

    }

    @Test
    public void testModify()
    {

    }

    @Test
    public void testModifyNull()
    {

    }

    @Test
    public void testModifyInvalid()
    {

    }
}
