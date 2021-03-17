package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AssistantRepository;
import ca.mcgill.ecse321.scrs.model.Assistant;
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
public class TestAssistantService
{
    @Mock
    private AssistantRepository assistantDao;

    @InjectMocks
    private AssistantService service;

    private static final int testID = 1;
    private static final int wrongID = 420;
    private static final String testString = "test";
    private static final String wrongString = "wrong";
    private static final String emptyString = "";

    @BeforeEach
    public void setMockOutput()
    {
        lenient().when(assistantDao.findByScrsUserId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testID))
            {
                Assistant assistant = new Assistant();
                assistant.setPhone(testString);
                assistant.setPassword(testString);
                assistant.setName(testString);
                assistant.setEmail(testString);
                assistant.setScrsUserId(testID);
                return assistant;
            } else
            {
                return null;
            }
        });
        lenient().when(assistantDao.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Assistant assistant = new Assistant();
                assistant.setPhone(testString);
                assistant.setPassword(testString);
                assistant.setName(testString);
                assistant.setEmail(testString);
                assistant.setScrsUserId(testID);
                return assistant;
            } else
            {
                return null;
            }
        });
        lenient().when(assistantDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Assistant assistant = new Assistant();
                assistant.setPhone(testString);
                assistant.setPassword(testString);
                assistant.setName(testString);
                assistant.setEmail(testString);
                assistant.setScrsUserId(testID);
                return assistant;
            } else
            {
                return null;
            }
        });
        lenient().when(assistantDao.findByPhone(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Assistant assistant = new Assistant();
                assistant.setPhone(testString);
                assistant.setPassword(testString);
                assistant.setName(testString);
                assistant.setEmail(testString);
                assistant.setScrsUserId(testID);
                return assistant;
            } else
            {
                return null;
            }
        });
        lenient().when(assistantDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            Assistant assistant = new Assistant();
            assistant.setPhone(testString);
            assistant.setPassword(testString);
            assistant.setName(testString);
            assistant.setEmail(testString);
            assistant.setScrsUserId(testID);
            ArrayList<Assistant> list = new ArrayList<>();
            list.add(assistant);
            return list;
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(assistantDao.save(any(Assistant.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateAssistant()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.createAssistant(testString, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(assistant);
        assertEquals(testString, assistant.getName());
        assertEquals(testString, assistant.getEmail());
        assertEquals(testString, assistant.getPhone());
    }

    @Test
    public void testCreateAssistantNull()
    {
        String error = null;
        Assistant assistant = null;
        try
        {
            assistant = service.createAssistant(null, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(assistant);
        assertEquals("Please submit a valid email.", error);
    }

    @Test
    public void testCreateAssistantEmpty()
    {
        String error = null;
        Assistant assistant = null;
        try
        {
            assistant = service.createAssistant(emptyString, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(assistant);
        assertEquals("Please submit a valid email.", error);
    }

    @Test
    public void testGetAll()
    {
        ArrayList<Assistant> assistants = null;
        try
        {
            assistants = new ArrayList<Assistant>(service.getAllAssistants());

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(assistants);
        assertEquals(1, assistants.size());
        assertEquals(testString, assistants.get(0).getName());
    }

    @Test
    public void testGetByID()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.getAssistantByID(testID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(assistant);
        assertEquals(testString, assistant.getName());
        assertEquals(testString, assistant.getEmail());
        assertEquals(testString, assistant.getPhone());
    }

    @Test
    public void testGetByWrongID()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.getAssistantByID(wrongID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(assistant);
    }

    @Test
    public void testGetByEmail()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.getAssistantByEmail(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(assistant);
        assertEquals(testString, assistant.getName());
        assertEquals(testID, assistant.getScrsUserId());
        assertEquals(testString, assistant.getPhone());
    }

    @Test
    public void testGetByWrongEmail()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.getAssistantByEmail(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(assistant);
    }

    @Test
    public void testGetByPhone()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.getAssistantByPhone(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(assistant);
        assertEquals(testString, assistant.getName());
        assertEquals(testID, assistant.getScrsUserId());
        assertEquals(testString, assistant.getEmail());
    }

    @Test
    public void testGetByWrongPhone()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.getAssistantByPhone(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(assistant);
    }

    @Test
    public void testGetByName()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.getAssistantByName(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(assistant);
        assertEquals(testString, assistant.getEmail());
        assertEquals(testID, assistant.getScrsUserId());
        assertEquals(testString, assistant.getPhone());
    }

    @Test
    public void testGetByWrongName()
    {
        Assistant assistant = null;
        try
        {
            assistant = service.getAssistantByName(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(assistant);
    }

    @Test
    public void testUpdateAssistant()
    {
        Assistant before = null;
        Assistant after = null;
        try
        {
            before = service.createAssistant(wrongString, wrongString, wrongString, wrongString);
            Assistant dummy = new Assistant();
            dummy.setEmail(testString);
            dummy.setName(testString);
            dummy.setPassword(testString);
            dummy.setPhone(testString);
            dummy.setScrsUserId(before.getScrsUserId());
            after = service.updateAssistantInfo(dummy);
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
    public void testUpdateAssistantNull()
    {
        String error = null;
        Assistant before = null;
        Assistant after = null;
        try
        {
            before = service.createAssistant(wrongString, wrongString, wrongString, wrongString);
            after = service.updateAssistantInfo(null);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertEquals("Please submit a valid user object.", error);
        assertNotEquals(before, after);
    }

    @Test
    public void testUpdateAssistantInvalidParams()
    {
        String error = null;
        Assistant before = null;
        Assistant after = null;
        try
        {
            before = service.createAssistant(wrongString, wrongString, wrongString, wrongString);
            Assistant dummy = new Assistant();
            dummy.setEmail(null);
            dummy.setName(testString);
            dummy.setPassword(testString);
            dummy.setPhone(testString);
            dummy.setScrsUserId(before.getScrsUserId());
            after = service.updateAssistantInfo(dummy);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertEquals("Please submit a valid email.", error);
        assertNotEquals(before, after);
    }

    @Test
    public void testDeleteAssistant()
    {
        Assistant assistant = null;
        Assistant deleted = null;
        try
        {
            assistant = service.createAssistant(wrongString, wrongString, wrongString, wrongString);
            deleted = service.deleteAssistant(assistant);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(deleted);
        assertEquals(assistant.getName(), deleted.getName());
        assertEquals(assistant.getEmail(), deleted.getEmail());
        assertEquals(assistant.getPhone(), deleted.getPhone());

    }

    @Test
    public void testDeleteAssistantNull()
    {
        Assistant deleted = null;
        try
        {
            deleted = service.deleteAssistant(null);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(deleted);
    }
}
