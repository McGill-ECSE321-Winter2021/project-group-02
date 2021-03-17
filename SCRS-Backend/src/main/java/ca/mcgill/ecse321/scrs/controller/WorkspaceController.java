package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.dto.WorkspaceDto;
import ca.mcgill.ecse321.scrs.model.Workspace;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import ca.mcgill.ecse321.scrs.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

@RestController
@RequestMapping(path = "/api/workspace")
public class WorkspaceController
{
    @Autowired
    TimeslotService timeslotService;
    @Autowired
    WorkspaceService workspaceService;
    @Autowired
    SCRSUserService scrsUserService;

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<WorkspaceDto> createWorkspace(@RequestParam(value = "name") String workspaceName, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to add a workspace.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You do not have permission to create a workspace.");
        }
        if (workspaceName == null)
        {
            throw new IllegalArgumentException("Invalid workspace name.");
        }
        return new ResponseEntity<>(convertToDto(workspaceService.createWorkspace(workspaceName)), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<WorkspaceDto> deleteWorkspace(@RequestParam(value = "id") int workspaceId, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to delete a workspace.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You do not have permission to edit workspaces.");
        }
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        if (workspace == null)
        {
            throw new IllegalArgumentException("Invalid workspace ID. Please submit a valid workspace to be deleted.");
        }
        return new ResponseEntity<>(convertToDto(workspaceService.deleteWorkspace(workspace)), HttpStatus.OK);
    }

    @GetMapping("/availabilities/{id}")
    public ResponseEntity<List<TimeslotDto>> getAllAvailableTimeslotsByWorkspace(@PathVariable("id") String workspaceId, @CookieValue(value = "id", defaultValue = "-1") String ID)
    {
        int id = Integer.parseInt(ID);
        int workspaceID = Integer.parseInt(workspaceId);
        if (id == -1)
        {
            throw new IllegalArgumentException("Please login to view the workspace availabilities.");
        }
        if(!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to view
        {
            throw new IllegalArgumentException("You do not have permission to view workspace availabilities.");
        }
        Workspace workspace= workspaceService.getWorkspaceById(workspaceID);
        if(workspace==null)
        {
            throw new IllegalArgumentException("Invalid workspace. Please submit a valid workspace to view the availabilities.");

        }
        return new ResponseEntity<>(convertToDto(timeslotService.getTimeslotsByWorkspace(workspace)),HttpStatus.OK);

    }


}
