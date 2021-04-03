package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.WorkspaceRepository;
import ca.mcgill.ecse321.scrs.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class WorkspaceService
{

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Transactional
    public Workspace createWorkspace(String name)
    {
        if (name == null || name.trim().length() == 0) throw new IllegalArgumentException("Invalid workspace name.");
        Workspace workspace = new Workspace();
        workspace.setSpaceName(name);
        workspaceRepository.save(workspace);
        return workspace;
    }

    @Transactional
    public Workspace updateWorkspace(Workspace workspace)
    {
        if (workspace == null || workspaceRepository.findByWorkspaceID(workspace.getWorkspaceID()) == null) throw new IllegalArgumentException("Invalid workspace");
        workspaceRepository.save(workspace);
        return workspace;
    }

    @Transactional
    public List<Workspace> getAllWorkspaces()
    {
        return toList(workspaceRepository.findAll());
    }

    @Transactional
    public Workspace getWorkspaceById(int id)
    {
        return workspaceRepository.findByWorkspaceID(id);
    }

    @Transactional
    public Workspace getWorkspaceByName(String name)
    {
        if (name == null || name.trim().length() == 0) throw new IllegalArgumentException("Invalid workspace name.");
        return workspaceRepository.findBySpaceName(name);
    }

    @Transactional
    public Workspace deleteWorkspace(Workspace workspace)
    {
        workspaceRepository.delete(workspace);
        return workspace;
    }
}
