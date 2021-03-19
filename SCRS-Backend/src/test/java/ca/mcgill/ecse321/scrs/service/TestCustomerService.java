package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.model.Customer;
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
public class TestCustomerService
{
    @Mock
    private CustomerRepository customerDao;

    @InjectMocks
    private CustomerService service;

    private static final int testID = 1;
    private static final int wrongID = 420;
    private static final String testString = "test";
    private static final String wrongString = "wrong";
    private static final String emptyString = "";

    @BeforeEach
    public void setMockOutput()
    {
        lenient().when(customerDao.findByScrsUserId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testID))
            {
                Customer customer = new Customer();
                customer.setPhone(testString);
                customer.setPassword(testString);
                customer.setName(testString);
                customer.setEmail(testString);
                customer.setScrsUserId(testID);
                return customer;
            } else
            {
                return null;
            }
        });
        lenient().when(customerDao.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Customer customer = new Customer();
                customer.setPhone(testString);
                customer.setPassword(testString);
                customer.setName(testString);
                customer.setEmail(testString);
                customer.setScrsUserId(testID);
                return customer;
            } else
            {
                return null;
            }
        });
        lenient().when(customerDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Customer customer = new Customer();
                customer.setPhone(testString);
                customer.setPassword(testString);
                customer.setName(testString);
                customer.setEmail(testString);
                customer.setScrsUserId(testID);
                return customer;
            } else
            {
                return null;
            }
        });
        lenient().when(customerDao.findByPhone(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testString))
            {
                Customer customer = new Customer();
                customer.setPhone(testString);
                customer.setPassword(testString);
                customer.setName(testString);
                customer.setEmail(testString);
                customer.setScrsUserId(testID);
                return customer;
            } else
            {
                return null;
            }
        });
        lenient().when(customerDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            Customer customer = new Customer();
            customer.setPhone(testString);
            customer.setPassword(testString);
            customer.setName(testString);
            customer.setEmail(testString);
            customer.setScrsUserId(testID);
            ArrayList<Customer> list = new ArrayList<>();
            list.add(customer);
            return list;
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateCustomer()
    {
        Customer customer = null;
        try
        {
            customer = service.createCustomer(testString, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(customer);
        assertEquals(testString, customer.getName());
        assertEquals(testString, customer.getEmail());
        assertEquals(testString, customer.getPhone());
    }

    @Test
    public void testCreateCustomerNull()
    {
        String error = null;
        Customer customer = null;
        try
        {
            customer = service.createCustomer(null, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Please submit a valid email.", error);
    }

    @Test
    public void testCreateCustomerEmpty()
    {
        String error = null;
        Customer customer = null;
        try
        {
            customer = service.createCustomer(emptyString, testString, testString, testString);

        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Please submit a valid email.", error);
    }

    @Test
    public void testGetAll()
    {
        ArrayList<Customer> customers = null;
        try
        {
            customers = new ArrayList<Customer>(service.getAllCustomers());

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals(testString, customers.get(0).getName());
    }

    @Test
    public void testGetByID()
    {
        Customer customer = null;
        try
        {
            customer = service.getCustomerByID(testID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(customer);
        assertEquals(testString, customer.getName());
        assertEquals(testString, customer.getEmail());
        assertEquals(testString, customer.getPhone());
    }

    @Test
    public void testGetByWrongID()
    {
        Customer customer = null;
        try
        {
            customer = service.getCustomerByID(wrongID);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(customer);
    }

    @Test
    public void testGetByEmail()
    {
        Customer customer = null;
        try
        {
            customer = service.getCustomerByEmail(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(customer);
        assertEquals(testString, customer.getName());
        assertEquals(testID, customer.getScrsUserId());
        assertEquals(testString, customer.getPhone());
    }

    @Test
    public void testGetByWrongEmail()
    {
        Customer customer = null;
        try
        {
            customer = service.getCustomerByEmail(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(customer);
    }

    @Test
    public void testGetByPhone()
    {
        Customer customer = null;
        try
        {
            customer = service.getCustomerByPhone(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(customer);
        assertEquals(testString, customer.getName());
        assertEquals(testID, customer.getScrsUserId());
        assertEquals(testString, customer.getEmail());
    }

    @Test
    public void testGetByWrongPhone()
    {
        Customer customer = null;
        try
        {
            customer = service.getCustomerByPhone(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(customer);
    }

    @Test
    public void testGetByName()
    {
        Customer customer = null;
        try
        {
            customer = service.getCustomerByName(testString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(customer);
        assertEquals(testString, customer.getEmail());
        assertEquals(testID, customer.getScrsUserId());
        assertEquals(testString, customer.getPhone());
    }

    @Test
    public void testGetByWrongName()
    {
        Customer customer = null;
        try
        {
            customer = service.getCustomerByName(wrongString);

        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(customer);
    }

    @Test
    public void testUpdateCustomer()
    {
        Customer before = null;
        Customer after = null;
        try
        {
            before = service.createCustomer(wrongString, wrongString, wrongString, wrongString);
            Customer dummy = new Customer();
            dummy.setEmail(testString);
            dummy.setName(testString);
            dummy.setPassword(testString);
            dummy.setPhone(testString);
            dummy.setScrsUserId(before.getScrsUserId());
            after = service.updateCustomerInfo(dummy);
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
    public void testUpdateCustomerNull()
    {
        String error = null;
        Customer before = null;
        Customer after = null;
        try
        {
            before = service.createCustomer(wrongString, wrongString, wrongString, wrongString);
            after = service.updateCustomerInfo(null);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertEquals("Please submit a valid user object.", error);
        assertNotEquals(before, after);
    }

    @Test
    public void testUpdateCustomerInvalidParams()
    {
        String error = null;
        Customer before = null;
        Customer after = null;
        try
        {
            before = service.createCustomer(wrongString, wrongString, wrongString, wrongString);
            Customer dummy = new Customer();
            dummy.setEmail(null);
            dummy.setName(testString);
            dummy.setPassword(testString);
            dummy.setPhone(testString);
            dummy.setScrsUserId(before.getScrsUserId());
            after = service.updateCustomerInfo(dummy);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(after);
        assertEquals("Please submit a valid email.", error);
        assertNotEquals(before, after);
    }

    @Test
    public void testDeleteCustomer()
    {
        Customer customer = null;
        Customer deleted = null;
        try
        {
            customer = service.createCustomer(wrongString, wrongString, wrongString, wrongString);
            deleted = service.deleteCustomer(customer);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNotNull(deleted);
        assertEquals(customer.getName(), deleted.getName());
        assertEquals(customer.getEmail(), deleted.getEmail());
        assertEquals(customer.getPhone(), deleted.getPhone());

    }

    @Test
    public void testDeleteCustomerNull()
    {
        Customer deleted = null;
        try
        {
            deleted = service.deleteCustomer(null);
        } catch (IllegalArgumentException e)
        {
            fail();
        }
        assertNull(deleted);
    }
}
