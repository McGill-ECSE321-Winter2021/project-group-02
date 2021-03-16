package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.AssistantDto;
import ca.mcgill.ecse321.scrs.dto.WorkspaceDto;
import ca.mcgill.ecse321.scrs.model.Assistant;
import ca.mcgill.ecse321.scrs.model.Workspace;
import ca.mcgill.ecse321.scrs.service.SCRSUserService;
import ca.mcgill.ecse321.scrs.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ca.mcgill.ecse321.scrs.controller.Helper.*;

public class WorkspaceController
{
    @Autowired
    WorkspaceService workspaceService;
    @Autowired
    SCRSUserService scrsUserService;

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<WorkspaceDto> createWorkspace(@RequestParam(value = "name") String workspaceName, @CookieValue(value = "id") int id)
    {
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You do not have permission to create a workspace.");
        }
        if (workspaceName == null)
        {
            throw new IllegalArgumentException("Invalid workspace name.");
        }
        return new ResponseEntity<WorkspaceDto>(convertToDto(workspaceService.createWorkspace(workspaceName)), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<WorkspaceDto> deleteWorkspace(@RequestParam(value = "id") int workspaceId, @CookieValue(value = "id") int id)
    {
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        if (workspace == null)
        {
            throw new IllegalArgumentException("Invalid workspace ID. Please submit a valid workspace to be deleted.");
        }
        if (!isAdmin(scrsUserService.getSCRSUserByID(id))) //does not have permission to edit.
        {
            throw new IllegalArgumentException("You do not have permission to edit workspaces.");
        }
        return new ResponseEntity<WorkspaceDto>(convertToDto(workspaceService.deleteWorkspace(workspace)), HttpStatus.OK);
    }
}
