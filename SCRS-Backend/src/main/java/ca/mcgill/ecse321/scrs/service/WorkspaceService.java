package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.WorkspaceRepository;
import ca.mcgill.ecse321.scrs.model.SCRSUser;
import ca.mcgill.ecse321.scrs.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class WorkspaceService {

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Transactional
    public List<Workspace> getAllWorkspaces() {
        return toList(workspaceRepository.findAll());
    }
}
