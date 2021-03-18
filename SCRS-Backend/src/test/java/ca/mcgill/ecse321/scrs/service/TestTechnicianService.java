package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.TechnicianRepository;
import ca.mcgill.ecse321.scrs.model.Technician;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestTechnicianService
{
    @Mock
    private TechnicianRepository technicianDao;

    @InjectMocks
    private TechnicianService service;
    
    private static final int testID = 1;
    private static final int wrongID = 420;
    private static final String testString = "test";
    private static final String wrongString = "wrong";
    private static final String emptyString = "";

    @BeforeEach
    public void setMockOutput()
    {
        lenient().when(technicianDao.findByScrsUserId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testID))
            {
                Technician technician = new Technician();
                technician.setPhone(testString);
                technician.setPassword(testString);
                technician.setName(testString);
                technician.setEmail(testString);
                technician.setScrsUserId(testID);
                return technician;
            } else
            {
                return null;
            }
        });
        lenient().when(technicianDao.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Technician technician = new Technician();
                technician.setPhone(testString);
                technician.setPassword(testString);
                technician.setName(testString);
                technician.setEmail(testString);
                technician.setScrsUserId(testID);
                return technician;
            } else
            {
                return null;
            }
        });
        lenient().when(technicianDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Technician technician = new Technician();
                technician.setPhone(testString);
                technician.setPassword(testString);
                technician.setName(testString);
                technician.setEmail(testString);
                technician.setScrsUserId(testID);
                return technician;
            } else
            {
                return null;
            }
        });
        lenient().when(technicianDao.findByPhone(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Technician technician = new Technician();
                technician.setPhone(testString);
                technician.setPassword(testString);
                technician.setName(testString);
                technician.setEmail(testString);
                technician.setScrsUserId(testID);
                return technician;
            } else
            {
                return null;
            }
        });
        lenient().when(technicianDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            Technician technician = new Technician();
            technician.setPhone(testString);
            technician.setPassword(testString);
            technician.setName(testString);
            technician.setEmail(testString);
            technician.setScrsUserId(testID);
            ArrayList<Technician> list = new ArrayList<>();
            list.add(technician);
            return list;
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(technicianDao.save(any(Technician.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateTechnician()
    {
        Technician technician = null;
        try
        {
            technician = service.createTechnician(testString, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(technician);
        assertEquals(testString, technician.getName());
        assertEquals(testString, technician.getEmail());
        assertEquals(testString, technician.getPhone());
    }

    @Test
    public void testCreateTechnicianNull()
    {
        String error = null;
        Technician technician = null;
        try
        {
            technician = service.createTechnician(null, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(technician);
        assertEquals("Please submit a valid email.", error);
    }

    @Test
    public void testCreateTechnicianEmpty()
    {
        String error = null;
        Technician technician = null;
        try
        {
            technician = service.createTechnician(emptyString, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(technician);
        assertEquals("Please submit a valid email.", error);
    }

    @Test
    public void testGetAll()
    {
        ArrayList<Technician> technicians = null;
        try
        {
            technicians = new ArrayList<Technician>(service.getAllTechnicians());

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(technicians);
        assertEquals(1, technicians.size());
        assertEquals(testString, technicians.get(0).getName());
    }

    @Test
    public void testGetByID()
    {
        Technician technician = null;
        try
        {
            technician = service.getTechnicianByID(testID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(technician);
        assertEquals(testString, technician.getName());
        assertEquals(testString, technician.getEmail());
        assertEquals(testString, technician.getPhone());
    }

    @Test
    public void testGetByWrongID()
    {
        Technician technician = null;
        try
        {
            technician = service.getTechnicianByID(wrongID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(technician);
    }

    @Test
    public void testGetByEmail()
    {
        Technician technician = null;
        try
        {
            technician = service.getTechnicianByEmail(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(technician);
        assertEquals(testString, technician.getName());
        assertEquals(testID, technician.getScrsUserId());
        assertEquals(testString, technician.getPhone());
    }

    @Test
    public void testGetByWrongEmail()
    {
        Technician technician = null;
        try
        {
            technician = service.getTechnicianByEmail(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(technician);
    }

    @Test
    public void testGetByPhone()
    {
        Technician technician = null;
        try
        {
            technician = service.getTechnicianByPhone(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(technician);
        assertEquals(testString, technician.getName());
        assertEquals(testID, technician.getScrsUserId());
        assertEquals(testString, technician.getEmail());
    }

    @Test
    public void testGetByWrongPhone()
    {
        Technician technician = null;
        try
        {
            technician = service.getTechnicianByPhone(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(technician);
    }

    @Test
    public void testGetByName()
    {
        Technician technician = null;
        try
        {
            technician = service.getTechnicianByName(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(technician);
        assertEquals(testString, technician.getEmail());
        assertEquals(testID, technician.getScrsUserId());
        assertEquals(testString, technician.getPhone());
    }

    @Test
    public void testGetByWrongName()
    {
        Technician technician = null;
        try
        {
            technician = service.getTechnicianByName(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(technician);
    }

    @Test
    public void testUpdateTechnician()
    {
        Technician before = null;
        Technician after = null;
        try
        {
            before = service.createTechnician(wrongString, wrongString, wrongString, wrongString);
            Technician dummy = new Technician();
            dummy.setEmail(testString);
            dummy.setName(testString);
            dummy.setPassword(testString);
            dummy.setPhone(testString);
            dummy.setScrsUserId(before.getScrsUserId());
            after = service.updateTechnicianInfo(dummy);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(after);
        assertEquals(wrongString, before.getName());
        assertEquals(wrongString, before.getEmail());
        assertEquals(wrongString, before.getPhone());
        assertEquals(testString, after.getName());
        assertEquals(testString, after.getEmail());
        assertEquals(testString, after.getPhone());
    }

    @Test
    public void testUpdateTechnicianNull()
    {
        String error = null;
        Technician before = null;
        Technician after = null;
        try
        {
            before = service.createTechnician(wrongString, wrongString, wrongString, wrongString);
            after = service.updateTechnicianInfo(null);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertEquals("Please submit a valid user object.", error);
        assertNotEquals(before, after);
    }

    @Test
    public void testUpdateTechnicianInvalidParams()
    {
        String error = null;
        Technician before = null;
        Technician after = null;
        try
        {
            before = service.createTechnician(wrongString, wrongString, wrongString, wrongString);
            Technician dummy = new Technician();
            dummy.setEmail(null);
            dummy.setName(testString);
            dummy.setPassword(testString);
            dummy.setPhone(testString);
            dummy.setScrsUserId(before.getScrsUserId());
            after = service.updateTechnicianInfo(dummy);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertEquals("Please submit a valid email.", error);
        assertNotEquals(before, after);
    }

    @Test
    public void testDeleteTechnician()
    {
        Technician technician = null;
        Technician deleted = null;
        try
        {
            technician = service.createTechnician(wrongString, wrongString, wrongString, wrongString);
            deleted = service.deleteTechnician(technician);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(deleted);
        assertEquals(technician.getName(), deleted.getName());
        assertEquals(technician.getEmail(), deleted.getEmail());
        assertEquals(technician.getPhone(), deleted.getPhone());

    }

    @Test
    public void testDeleteTechnicianNull()
    {
        Technician deleted = null;
        try
        {
            deleted = service.deleteTechnician(null);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(deleted);
    }
}
