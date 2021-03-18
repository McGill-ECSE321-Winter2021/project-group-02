package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.WorkspaceRepository;
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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestWorkspaceService
{
    @Mock
    WorkspaceRepository workspaceDao;

    @InjectMocks
    WorkspaceService service;

    private static final int testID = 1;
    private static final int wrongID = 420;
    private static final String testName = "test";
    private static final String wrongName = "wrong";
    private static final Workspace testWorkspace = new Workspace();

    @BeforeAll
    public static void instantiateVars()
    {
        testWorkspace.setWorkspaceID(testID);
        testWorkspace.setSpaceName(testName);
    }

    @BeforeEach
    public void setMockOutput()
    {
        lenient().when(workspaceDao.findByWorkspaceID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testID))
            {
                return testWorkspace;
            } else
            {
                return null;
            }
        });
        lenient().when(workspaceDao.findBySpaceName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(testName))
            {
                return testWorkspace;
            } else
            {
                return null;
            }
        });
        lenient().when(workspaceDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            ArrayList<Workspace> list = new ArrayList<>();
            list.add(testWorkspace);
            return list;
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(workspaceDao.save(any(Workspace.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreate()
    {
        Workspace workspace = null;
        try
        {
            workspace = service.createWorkspace(testName);
        } catch (IllegalArgumentException e)
        {
            fail(e);
        }
        assertNotNull(workspace);
        assertEquals(testName, workspace.getSpaceName());
    }

    @Test
    public void testCreateNullName()
    {
        String error = null;
        Workspace workspace = null;
        try
        {
            workspace = service.createWorkspace(null);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(workspace);
        assertNotNull(error);
        assertEquals("Invalid workspace name.", error);
    }

    @Test
    public void testCreateInvalidName()
    {
        String error = null;
        Workspace workspace = null;
        try
        {
            workspace = service.createWorkspace(" ");
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(workspace);
        assertNotNull(error);
        assertEquals("Invalid workspace name.", error);
    }

    @Test
    public void testGetAll()
    {
        ArrayList<Workspace> workspaces = null;
        try
        {
            workspaces = new ArrayList<>(service.getAllWorkspaces());
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(workspaces);
        assertEquals(1, workspaces.size());
        assertEquals(testName, workspaces.get(0).getSpaceName());
    }

    @Test
    public void testGetByID()
    {
        Workspace workspace = null;
        try
        {
            workspace = service.getWorkspaceById(testID);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(workspace);
        assertEquals(testName, workspace.getSpaceName());
        assertEquals(testID, workspace.getWorkspaceID());
    }

    @Test
    public void testGetByWrongID()
    {
        Workspace workspace = null;
        try
        {
            workspace = service.getWorkspaceById(wrongID);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNull(workspace);
    }

    @Test
    public void testGetByName()
    {
        Workspace workspace = null;
        try
        {
            workspace = service.getWorkspaceByName(testName);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(workspace);
        assertEquals(testName, workspace.getSpaceName());
        assertEquals(testID, workspace.getWorkspaceID());
    }

    @Test
    public void testGetByWrongName()
    {
        Workspace workspace = null;
        try
        {
            workspace = service.getWorkspaceByName(wrongName);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNull(workspace);
    }

    @Test
    public void testGetByNullName()
    {
        String error = null;
        Workspace workspace = null;
        try
        {
            workspace = service.getWorkspaceByName(null);
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(workspace);
        assertEquals("Invalid workspace name.", error);
    }

    @Test
    public void testGetByInvalidName()
    {
        String error = null;
        Workspace workspace = null;
        try
        {
            workspace = service.getWorkspaceByName(" ");
        } catch (IllegalArgumentException e)
        {
            error = e.getMessage();
        }
        assertNull(workspace);
        assertEquals("Invalid workspace name.", error);
    }

    @Test
    public void testDelete()
    {
        Workspace workspace = testWorkspace;
        Workspace deleted = null;
        try
        {
            deleted = service.deleteWorkspace(workspace);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNotNull(deleted);
        assertEquals(workspace.getWorkspaceID(), deleted.getWorkspaceID());
        assertEquals(workspace.getSpaceName(), deleted.getSpaceName());
        assertEquals(workspace, deleted);
    }

    @Test
    public void testDeleteNull()
    {
        Workspace deleted = null;
        try
        {
            deleted = service.deleteWorkspace(null);
        } catch (IllegalArgumentException e)
        {
            fail(e.getMessage());
        }
        assertNull(deleted);
    }

}
