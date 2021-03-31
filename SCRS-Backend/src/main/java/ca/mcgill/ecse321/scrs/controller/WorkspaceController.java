package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.CustomerDto;
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

import java.util.ArrayList;
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
    @CrossOrigin(origins = "*")
    public ResponseEntity<WorkspaceDto> createWorkspace(@RequestParam(value = "name") String workspaceName)
    {
        if (workspaceName == null)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            return new ResponseEntity<>(convertToDto(workspaceService.createWorkspace(workspaceName)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/update", "/update/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<WorkspaceDto> updateWorkspaceName(@RequestBody WorkspaceDto workspace) {
        if (workspace == null)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Workspace updated = workspaceService.getWorkspaceById(workspace.getWorkspaceId());
        if (updated == null)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            updated.setSpaceName(workspace.getSpaceName());
            return new ResponseEntity<>(convertToDto(workspaceService.updateWorkspace(updated)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/delete/{id}", "/delete/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<WorkspaceDto> deleteWorkspace(@PathVariable("id") int workspaceId)
    {
        try {
            Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
            if (workspace == null)
            {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(convertToDto(workspaceService.deleteWorkspace(workspace)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/availabilities/{id}","/availabilities/{id}/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<TimeslotDto>> getAllAvailableTimeslotsByWorkspace(@PathVariable("id") int workspaceId)
    {
        try {
            Workspace workspace= workspaceService.getWorkspaceById(workspaceId);
            if(workspace==null)
            {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(convertToDto(timeslotService.getTimeslotsByWorkspace(workspace)),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = {"/getall","/getall/"})
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<WorkspaceDto>> getAll()
    {
        try {
            ArrayList<Workspace> workspaces = new ArrayList<>(workspaceService.getAllWorkspaces());
            return new ResponseEntity<>(convertListToDto(workspaces),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
