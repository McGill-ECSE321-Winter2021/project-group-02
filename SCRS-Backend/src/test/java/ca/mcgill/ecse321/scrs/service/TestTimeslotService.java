package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AppointmentRepository;
import ca.mcgill.ecse321.scrs.dao.TechnicianRepository;
import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
import ca.mcgill.ecse321.scrs.dao.WorkspaceRepository;
import ca.mcgill.ecse321.scrs.model.Technician;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.model.Workspace;
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
public class TestTimeslotService
{
    @Mock
    private TimeslotRepository timeslotDao;
    @Mock
    private AppointmentRepository appointmentDao;
    @Mock
    private WorkspaceRepository workspaceDao;
    @Mock
    private TechnicianRepository technicianDao;

    @InjectMocks
    private TimeslotService service;

    private static final int testID = 1;
    private static final int testID2 = 2;
    private static final int testID3 = 3;
    private static final int wrongID = 420;
    private static final Time testTime = new Time(currentTimeMillis());
    private static final Time laterTime = new Time(currentTimeMillis() + 100000000);
    private static final Time latestTime = new Time(currentTimeMillis() + 900000000);
    private static final Date testDate = new Date(currentTimeMillis());
    private static final Date laterDate = new Date(currentTimeMillis() + 100000000);
    private static final Date latestDate = new Date(currentTimeMillis() + 900000000);
    private static final Workspace testWorkspace = new Workspace();
    private static final Technician testTechnician = new Technician();
    private static final Timeslot testTimeslot = new Timeslot();

    @BeforeAll
    public static void instantiateVars()
    {
        testWorkspace.setWorkspaceID(testID);
        testWorkspace.setSpaceName("name");
        testWorkspace.setAvailabilities(new ArrayList<>());

        testTechnician.setEmail("email");
        testTechnician.setName("name");
        testTechnician.setPhone("phone");
        testTechnician.setScrsUserId(testID);

        testTimeslot.setWorkspace(testWorkspace);
        testTimeslot.setStartTime(testTime);
        testTimeslot.setEndTime(laterTime);
        testTimeslot.setStartDate(testDate);
        testTimeslot.setEndDate(laterDate);
        testTimeslot.setTimeSlotID(testID);
        ArrayList<Technician> techs = new ArrayList<>();
        techs.add(testTechnician);
        testTimeslot.setTechnicians(techs);
    }

    @BeforeEach
    public void setMockOutput()
    {
        lenient().when(timeslotDao.findByTimeSlotID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            Timeslot timeslot = new Timeslot();
            timeslot.setWorkspace(testWorkspace);
            timeslot.setStartTime(testTime);
            timeslot.setEndTime(laterTime);
            timeslot.setStartDate(testDate);
            timeslot.setEndDate(laterDate);
            if (invocation.getArgument(0).equals(testID))
            {
                timeslot.setTimeSlotID(testID);
                return timeslot;
            } else if (invocation.getArgument(0).equals(testID2))
            {
                timeslot.setTimeSlotID(testID2);
                return timeslot;
            } else if (invocation.getArgument(0).equals(testID3))
            {
                timeslot.setTimeSlotID(testID3);
                return timeslot;
            } else
            {
                return null;
            }
        });
        lenient().when(timeslotDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            ArrayList<Timeslot> list = new ArrayList<>();
            Timeslot timeslot = new Timeslot();
            timeslot.setWorkspace(testWorkspace);
            timeslot.setTimeSlotID(testID);
            timeslot.setStartTime(testTime);
            timeslot.setEndTime(laterTime);
            timeslot.setStartDate(testDate);
            timeslot.setEndDate(laterDate);
            list.add(timeslot);
            return list;
        });
        lenient().when(timeslotDao.findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDate(any(Date.class), any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testDate) && invocation.getArgument(1).equals(laterDate))
            {
                ArrayList<Timeslot> list = new ArrayList<>();
                ArrayList<Technician> technicians = new ArrayList<>();
                technicians.add(testTechnician);
                list.add(testTimeslot);
                Timeslot timeslot2 = new Timeslot();
                timeslot2.setWorkspace(testWorkspace);
                timeslot2.setTimeSlotID(testID2);
                timeslot2.setStartTime(testTime);
                timeslot2.setEndTime(laterTime);
                timeslot2.setStartDate(laterDate);
                timeslot2.setEndDate(latestDate);
                timeslot2.setTechnicians(technicians);
                list.add(timeslot2);
                return list;
            } else
            {
                return null;
            }
        });
        lenient().when(timeslotDao.findByTechnicians(any(Technician.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testTechnician))
            {
                ArrayList<Timeslot> list = new ArrayList<>();
                list.add(testTimeslot);
                return list;
            } else
            {
                return null;
            }
        });
        lenient().when(timeslotDao.findByWorkspace(any(Workspace.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testWorkspace))
            {
                Timeslot timeslot = new Timeslot();
                timeslot.setWorkspace(testWorkspace);
                timeslot.setTimeSlotID(testID);
                timeslot.setStartTime(testTime);
                timeslot.setEndTime(laterTime);
                timeslot.setStartDate(testDate);
                timeslot.setEndDate(laterDate);

                ArrayList<Timeslot> list = new ArrayList<>();
                list.add(timeslot);
                return list;
            } else
            {
                return null;
            }
        });
        lenient().when(workspaceDao.findByWorkspaceID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testID))
            {
                return testWorkspace;
            } else
            {
                return null;
            }
        });
        lenient().when(technicianDao.findByScrsUserId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testID))
            {
                return testTechnician;
            } else
            {
                return null;
            }
        });
        lenient().when(appointmentDao.existsByTimeslots(any(Timeslot.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0).equals(testTimeslot));
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(timeslotDao.save(any(Timeslot.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreate()
    {
        Timeslot timeslot = null;
        try
        {
            timeslot = service.createTimeslot(testDate, laterDate, testTime, laterTime, testWorkspace);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(timeslot);
        assertEquals(testDate, timeslot.getStartDate());
        assertEquals(testTime, timeslot.getStartTime());
        assertEquals(laterDate, timeslot.getEndDate());
        assertEquals(laterTime, timeslot.getEndTime());
    }

    @Test
    public void testCreateNullDate()
    {
        String error = null;
        Timeslot timeslot = null;
        try
        {
            timeslot = service.createTimeslot(null, laterDate, testTime, laterTime, testWorkspace);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslot);
        assertEquals("Please input a valid start and end date.", error);
    }

    @Test
    public void testCreateNullTime()
    {
        String error = null;
        Timeslot timeslot = null;
        try
        {
            timeslot = service.createTimeslot(testDate, laterDate, null, laterTime, testWorkspace);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslot);
        assertEquals("Please input a valid start and end time.", error);
    }

    @Test
    public void testCreateNullWorkspace()
    {
        String error = null;
        Timeslot timeslot = null;
        try
        {
            timeslot = service.createTimeslot(testDate, laterDate, testTime, laterTime, null);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslot);
        assertEquals("Please input a valid workspace.", error);
    }

    @Test
    public void testCreateInvalidDate()
    {
        String error = null;
        Timeslot timeslot = null;
        try
        {
            timeslot = service.createTimeslot(laterDate, testDate, testTime, laterTime, testWorkspace);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslot);
        assertEquals("Your start date cannot be after your end date.", error);
    }

    @Test
    public void testCreateInvalidTime()
    {
        String error = null;
        Timeslot timeslot = null;
        try
        {
            timeslot = service.createTimeslot(testDate, laterDate, laterTime, testTime, testWorkspace);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslot);
        assertEquals("Your start time cannot be after your end time.", error);
    }

    @Test
    public void testGetAll()
    {
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getAllTimeslots());
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(timeslots);
        assertNotEquals(0, timeslots.size());
        assertEquals(testID, timeslots.get(0).getTimeSlotID());
        assertEquals(testWorkspace, timeslots.get(0).getWorkspace());
        assertEquals(testDate, timeslots.get(0).getStartDate());
    }

    @Test
    public void testGetByID()
    {
        Timeslot timeslot = null;
        try
        {
            timeslot = service.getTimeslotById(testID);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(timeslot);
        assertEquals(testID, timeslot.getTimeSlotID());
        assertEquals(testDate, timeslot.getStartDate());
        assertEquals(testTime, timeslot.getStartTime());
        assertEquals(testWorkspace.getWorkspaceID(), timeslot.getWorkspace().getWorkspaceID());
    }

    @Test
    public void testGetMultipleByID()
    {
        ArrayList<Timeslot> timeslots = null;
        try
        {
            ArrayList<Integer> ids = new ArrayList<>();
            ids.add(testID);
            ids.add(testID2);
            ids.add(testID3);
            timeslots = new ArrayList<>(service.getTimeslotsById(ids));
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(timeslots);
        assertEquals(3, timeslots.size());
        assertEquals(testID, timeslots.get(0).getTimeSlotID());
        assertEquals(testID2, timeslots.get(1).getTimeSlotID());
        assertEquals(testID3, timeslots.get(2).getTimeSlotID());
    }

    @Test
    public void testGetMultipleByIDNull()
    {
        ArrayList<Timeslot> timeslots = null;
        String error = null;
        try
        {
            timeslots = new ArrayList<>(service.getTimeslotsById(null));
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslots);
        assertEquals("Please input at least one valid timeslot ID.", error);
    }

    @Test
    public void testGetByTechBetween()
    {
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getTimeslotsByTechnicianBetweenDates(testTechnician, testDate, laterDate));
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(timeslots);
        assertEquals(2, timeslots.size());
        assertEquals(testID, timeslots.get(0).getTimeSlotID());
        assertEquals(testID2, timeslots.get(1).getTimeSlotID());
    }

    @Test
    public void testGetByNullTechBetween()
    {
        String error = null;
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getTimeslotsByTechnicianBetweenDates(null, testDate, laterDate));
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslots);
        assertEquals("Invalid technician.", error);
    }

    @Test
    public void testGetByTechBetweenInvalid()
    {
        String error = null;
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getTimeslotsByTechnicianBetweenDates(testTechnician, latestDate, laterDate));
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslots);
        assertEquals("Your start date cannot be after your end date.", error);
    }

    @Test
    public void testGetByWorkspace()
    {
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getTimeslotsByWorkspace(testWorkspace));
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(timeslots);
        assertEquals(1, timeslots.size());
        assertEquals(testID, timeslots.get(0).getTimeSlotID());
        assertEquals(testDate, timeslots.get(0).getStartDate());
        assertEquals(testTime, timeslots.get(0).getStartTime());
        assertEquals(testWorkspace.getWorkspaceID(), timeslots.get(0).getWorkspace().getWorkspaceID());
    }

    @Test
    public void testGetByWorkspaceNull()
    {
        String error = null;
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getTimeslotsByWorkspace(null));
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslots);
        assertEquals("Invalid workspace.", error);
    }

    @Test
    public void testGetAvailable()
    {
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getAvailableTimeslots(testDate, laterDate));
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(timeslots);
        assertEquals(1, timeslots.size());
        assertEquals(testID2, timeslots.get(0).getTimeSlotID());
    }

    @Test
    public void testGetAvailableNull()
    {
        String error = null;
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getAvailableTimeslots(null, laterDate));
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslots);
        assertEquals("Please input a valid start and end date.", error);
    }

    @Test
    public void testGetAvailableInvalid()
    {
        String error = null;
        ArrayList<Timeslot> timeslots = null;
        try
        {
            timeslots = new ArrayList<>(service.getAvailableTimeslots(latestDate, laterDate));
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(timeslots);
        assertEquals("Your start date cannot be after your end date.", error);
    }

    @Test
    public void testAssign()
    {
        boolean assigned = false;
        try
        {
            Timeslot timeslot = new Timeslot();
            timeslot.setTechnicians(new ArrayList<>());
            timeslot.setTimeSlotID(testID);
            //not a valid timeslot, but we only test for existence, which is being mocked.
            assigned = service.assignTechnicianToTimeslot(testTechnician, timeslot);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertTrue(assigned);
    }

    @Test
    public void testAssignAlreadyAssigned()
    {
        boolean assigned = false;
        try
        {
            assigned = service.assignTechnicianToTimeslot(testTechnician, testTimeslot);
            //testTechnician and testTimeslot are already assigned to each other
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertFalse(assigned);
    }

    @Test
    public void testAssignNullTech()
    {
        String error = null;
        boolean assigned = false;
        try
        {
            Timeslot timeslot = new Timeslot();
            timeslot.setTechnicians(new ArrayList<>());
            timeslot.setTimeSlotID(testID);
            //not a valid timeslot, but we only test for existence, which is being mocked.
            assigned = service.assignTechnicianToTimeslot(null, timeslot);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertFalse(assigned);
        assertEquals("Invalid technician.", error);
    }

    @Test
    public void testAssignNullTimeslot()
    {
        String error = null;
        boolean assigned = false;
        try
        {
            assigned = service.assignTechnicianToTimeslot(testTechnician, null);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertFalse(assigned);
        assertEquals("Invalid timeslot.", error);
    }

    @Test
    public void testDelete()
    {
        Timeslot timeslot = testTimeslot;
        Timeslot deleted = null;
        try
        {
            deleted = service.deleteTimeslot(testTimeslot);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(deleted);
        assertEquals(timeslot.getTimeSlotID(), deleted.getTimeSlotID());
        assertEquals(timeslot.getStartDate(), deleted.getStartDate());
        assertEquals(timeslot.getTechnicians(), deleted.getTechnicians());
    }

    @Test
    public void testDeleteNull()
    {

        Timeslot deleted = null;
        try
        {
            deleted = service.deleteTimeslot(null);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(deleted);
    }
}
