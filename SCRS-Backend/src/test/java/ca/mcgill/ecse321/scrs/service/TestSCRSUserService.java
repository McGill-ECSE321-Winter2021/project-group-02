package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.SCRSUserRepository;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.SCRSUser;
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
public class TestSCRSUserService
{
    @Mock
    private SCRSUserRepository userDao;

    @InjectMocks
    private SCRSUserService service;

    private static final int testID = 1;
    private static final int wrongID = 420;
    private static final String testString = "test";
    private static final String wrongString = "wrong";
    private static final String emptyString = "";

    @BeforeEach
    public void setMockOutput()
    {
        lenient().when(userDao.findByScrsUserId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testID))
            {
                SCRSUser user = new Customer();
                user.setPhone(testString);
                user.setPassword(testString);
                user.setName(testString);
                user.setEmail(testString);
                user.setScrsUserId(testID);
                return user;
            } else
            {
                return null;
            }
        });
        lenient().when(userDao.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                SCRSUser user = new Customer();
                user.setPhone(testString);
                user.setPassword(testString);
                user.setName(testString);
                user.setEmail(testString);
                user.setScrsUserId(testID);
                return user;
            } else
            {
                return null;
            }
        });
        lenient().when(userDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                SCRSUser user = new Customer();
                user.setPhone(testString);
                user.setPassword(testString);
                user.setName(testString);
                user.setEmail(testString);
                user.setScrsUserId(testID);
                return user;
            } else
            {
                return null;
            }
        });
        lenient().when(userDao.findByPhone(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                SCRSUser user = new Customer();
                user.setPhone(testString);
                user.setPassword(testString);
                user.setName(testString);
                user.setEmail(testString);
                user.setScrsUserId(testID);
                return user;
            } else
            {
                return null;
            }
        });
        lenient().when(userDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            SCRSUser user = new Customer();
            user.setPhone(testString);
            user.setPassword(testString);
            user.setName(testString);
            user.setEmail(testString);
            user.setScrsUserId(testID);
            ArrayList<SCRSUser> list = new ArrayList<>();
            list.add(user);
            return list;
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(userDao.save(any(SCRSUser.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testGetAll()
    {
        ArrayList<SCRSUser> users = null;
        try
        {
            users = new ArrayList<SCRSUser>(service.getAllSCRSUsers());

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(testString, users.get(0).getName());
    }

    @Test
    public void testGetByID()
    {
        SCRSUser user = null;
        try
        {
            user = service.getSCRSUserByID(testID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(user);
        assertEquals(testString, user.getName());
        assertEquals(testString, user.getEmail());
        assertEquals(testString, user.getPhone());
    }

    @Test
    public void testGetByWrongID()
    {
        SCRSUser user = null;
        try
        {
            user = service.getSCRSUserByID(wrongID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(user);
    }

    @Test
    public void testGetByEmail()
    {
        SCRSUser user = null;
        try
        {
            user = service.getSCRSUserByEmail(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(user);
        assertEquals(testString, user.getName());
        assertEquals(testID, user.getScrsUserId());
        assertEquals(testString, user.getPhone());
    }

    @Test
    public void testGetByWrongEmail()
    {
        SCRSUser user = null;
        try
        {
            user = service.getSCRSUserByEmail(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(user);
    }

    @Test
    public void testGetByPhone()
    {
        SCRSUser user = null;
        try
        {
            user = service.getSCRSUserByPhone(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(user);
        assertEquals(testString, user.getName());
        assertEquals(testID, user.getScrsUserId());
        assertEquals(testString, user.getEmail());
    }

    @Test
    public void testGetByWrongPhone()
    {
        SCRSUser user = null;
        try
        {
            user = service.getSCRSUserByPhone(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(user);
    }

    @Test
    public void testGetByName()
    {
        SCRSUser user = null;
        try
        {
            user = service.getSCRSUserByName(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(user);
        assertEquals(testString, user.getEmail());
        assertEquals(testID, user.getScrsUserId());
        assertEquals(testString, user.getPhone());
    }

    @Test
    public void testGetByWrongName()
    {
        SCRSUser user = null;
        try
        {
            user = service.getSCRSUserByName(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(user);
    }
}
