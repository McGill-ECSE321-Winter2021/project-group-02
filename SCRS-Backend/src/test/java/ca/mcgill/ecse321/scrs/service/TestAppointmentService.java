package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AppointmentRepository;
import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

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
    private static final int testRating = 7;
    private static final int wrongRating = 100;
    private static final Time testTime = new Time(currentTimeMillis());
    private static final Date testDate = new Date(currentTimeMillis());
    private static final AppointmentType testType = AppointmentType.Maintenance;
    private static final AppointmentType wrongType = AppointmentType.Other;
    private static final Customer testCustomer = new Customer();
    private static final Timeslot testTimeslot = new Timeslot();
    private static final Timeslot wrongTimeslot = new Timeslot();
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
        wrongTimeslot.setStartTime(new Time(currentTimeMillis()-1000000000));
        wrongTimeslot.setEndTime(new Time(currentTimeMillis()-1000000000));
        wrongTimeslot.setStartDate(new Date(currentTimeMillis()-1000000000-1000000000-1000000000-1000000000));
        wrongTimeslot.setEndDate(new Date(currentTimeMillis()-1000000000-1000000000-1000000000-1000000000));
        wrongTimeslot.setTimeSlotID(wrongID);
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
        lenient().when(appointmentDao.findByTimeslotsContains(any(Timeslot.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testTimeslot))
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
        ArrayList<Appointment> appointments = null;
        try
        {
            appointments = new ArrayList<>(service.getAppointmentsByCustomer(testCustomer));

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(appointments);
        assertNotEquals(0, appointments.size());
        assertEquals(testID, appointments.get(0).getAppointmentID());
        assertEquals(testCustomer, appointments.get(0).getCustomer());
        assertEquals(timeslots, appointments.get(0).getTimeslots());
        assertEquals(testType, appointments.get(0).getAppointmentType());

    }

    @Test
    public void testGetByCustomerNull()
    {
        ArrayList<Appointment> appointments = null;
        try
        {
            appointments = new ArrayList<>(service.getAppointmentsByCustomer(null));

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(appointments);
        assertEquals(0, appointments.size());
    }

    @Test
    public void testGetByWrongCustomer()
    {
        ArrayList<Appointment> appointments = null;
        try
        {
            if (service.getAppointmentsByCustomer(new Customer()) != null)
            {
                appointments = new ArrayList<>(service.getAppointmentsByCustomer(new Customer()));
            }
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(appointments);
    }

    @Test
    public void testGetByTimeslot()
    {
        Appointment appointment = null;
        try
        {
            appointment = service.getAppointmentByTimeslot(testTimeslot);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(appointment);
        assertEquals(testType, appointment.getAppointmentType());
        assertEquals(testID, appointment.getAppointmentID());
        assertEquals(testCustomer, appointment.getCustomer());
        assertEquals(testType, appointment.getAppointmentType());
    }

    @Test
    public void testGetByTimeslotNull()
    {
        Appointment appointment = null;
        try
        {
            appointment = service.getAppointmentByTimeslot(null);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(appointment);
    }

    @Test
    public void testGetByWrongTimeslot()
    {
        Appointment appointment = null;
        try
        {
            appointment = service.getAppointmentByTimeslot(wrongTimeslot);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(appointment);
    }

    @Test
    public void testRate()
    {
        Appointment appointment = null;
        try
        {
            appointment = service.rateAppointment(testID, testRating);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(appointment);
        assertEquals(testCustomer.getScrsUserId(), appointment.getCustomer().getScrsUserId());
        assertEquals(testType, appointment.getAppointmentType());
        assertEquals(testTimeslot.getTimeSlotID(), appointment.getTimeslot(0).getTimeSlotID());
        assertEquals(testRating, appointment.getRating());
    }

    @Test
    public void testRateInvalidID()
    {
        String error = null;
        Appointment appointment = null;
        try
        {
            appointment = service.rateAppointment(wrongID, testRating);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals("No such appointment!", error);
    }

    @Test
    public void testRateInvalidRating()
    {
        String error = null;
        Appointment appointment = null;
        try
        {
            appointment = service.rateAppointment(testID, wrongRating);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals("Invalid rating", error);
    }

    @Test
    public void testModify()
    {
        Appointment before = null;
        Appointment after = null;
        try
        {
            before = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            before.setAppointmentID(testID);
            Appointment dummy = new Appointment();
            dummy.setAppointmentID(before.getAppointmentID());
            dummy.setAppointmentType(wrongType);
            dummy.setService("test");
            dummy.setNote("note");
            dummy.setPaid(true);
            dummy.setRating(9);
            dummy.setCustomer(before.getCustomer());
            dummy.setTimeslots(before.getTimeslots().toArray(new Timeslot[0]));
            after = service.modifyAppointment(dummy);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(after);
        assertNotNull(before);
        assertEquals(testType, before.getAppointmentType());
        assertNull(before.getService());
        assertNull(before.getNote());
        assertFalse(before.getPaid());
        assertEquals(wrongType, after.getAppointmentType());
        assertEquals("test", after.getService());
        assertEquals("note", after.getNote());
        assertTrue(after.getPaid());
    }

    @Test
    public void testModifyNull()
    {
        String error = null;
        Appointment before = null;
        Appointment after = null;
        try
        {
            before = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            before.setAppointmentID(testID);
            after = service.modifyAppointment(null);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertNotNull(before);
        assertEquals(testType, before.getAppointmentType());
        assertNull(before.getService());
        assertNull(before.getNote());
        assertFalse(before.getPaid());
        assertEquals("Invalid appointment", error);

    }

    @Test
    public void testModifyInvalidType()
    {
        String error = null;
        Appointment before = null;
        Appointment after = null;
        try
        {
            before = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            before.setAppointmentID(testID);
            Appointment dummy = new Appointment();
            dummy.setAppointmentID(before.getAppointmentID());
            dummy.setAppointmentType(null);
            dummy.setRating(9);
            dummy.setCustomer(before.getCustomer());
            dummy.setTimeslots(before.getTimeslots().toArray(new Timeslot[0]));
            after = service.modifyAppointment(dummy);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertNotNull(before);
        assertEquals(testType, before.getAppointmentType());
        assertNull(before.getService());
        assertNull(before.getNote());
        assertFalse(before.getPaid());
        assertEquals("Invalid appointment type.", error);
    }

    @Test
    public void testModifyInvalidRating()
    {
        String error = null;
        Appointment before = null;
        Appointment after = null;
        try
        {
            before = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            before.setAppointmentID(testID);
            Appointment dummy = new Appointment();
            dummy.setAppointmentID(before.getAppointmentID());
            dummy.setAppointmentType(wrongType);
            dummy.setRating(999);
            dummy.setCustomer(before.getCustomer());
            dummy.setTimeslots(before.getTimeslots().toArray(new Timeslot[0]));
            after = service.modifyAppointment(dummy);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertNotNull(before);
        assertEquals(testType, before.getAppointmentType());
        assertNull(before.getService());
        assertNull(before.getNote());
        assertFalse(before.getPaid());
        assertEquals("Invalid rating", error);
    }

    @Test
    public void testModifyInvalidCustomer()
    {
        String error = null;
        Appointment before = null;
        Appointment after = null;
        try
        {
            before = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            before.setAppointmentID(testID);
            Appointment dummy = new Appointment();
            dummy.setAppointmentID(before.getAppointmentID());
            dummy.setAppointmentType(wrongType);
            dummy.setRating(9);
            dummy.setTimeslots(before.getTimeslots().toArray(new Timeslot[0]));
            after = service.modifyAppointment(dummy);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertNotNull(before);
        assertEquals(testType, before.getAppointmentType());
        assertNull(before.getService());
        assertNull(before.getNote());
        assertFalse(before.getPaid());
        assertEquals("Invalid customer.", error);
    }

    @Test
    public void testModifyInvalidTimeslots()
    {
        String error = null;
        Appointment before = null;
        Appointment after = null;
        try
        {
            before = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            before.setAppointmentID(testID);
            Appointment dummy = new Appointment();
            dummy.setAppointmentID(before.getAppointmentID());
            dummy.setAppointmentType(wrongType);
            dummy.setRating(9);
            dummy.setCustomer(before.getCustomer());
            after = service.modifyAppointment(dummy);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertNotNull(before);
        assertEquals(testType, before.getAppointmentType());
        assertNull(before.getService());
        assertNull(before.getNote());
        assertFalse(before.getPaid());
        assertEquals("No valid timeslots selected.", error);
    }

    @Test
    public void testDelete()
    {
        Appointment appointment = null;
        Appointment deleted = null;
        try
        {
            appointment = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            deleted = service.deleteAppointment(appointment);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(deleted);
        assertEquals(appointment.getAppointmentID(), deleted.getAppointmentID());
        assertEquals(appointment.getAppointmentType(), deleted.getAppointmentType());
        assertEquals(appointment.getCustomer(), deleted.getCustomer());
        assertEquals(appointment.getTimeslots(), deleted.getTimeslots());
        assertEquals(appointment.getNote(), deleted.getNote());
    }

    @Test
    public void testDeleteNull()
    {
        Appointment appointment = null;
        Appointment deleted = null;
        try
        {
            appointment = service.createAppointment(testType, null, null, false, testCustomer, timeslots.toArray(new Timeslot[0]));
            deleted = service.deleteAppointment(null);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(deleted);
    }
}
