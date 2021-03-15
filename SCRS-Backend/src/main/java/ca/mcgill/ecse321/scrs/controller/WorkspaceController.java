package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.WorkspaceDto;
import ca.mcgill.ecse321.scrs.model.Workspace;

public class WorkspaceController
{
    public static WorkspaceDto convertToDto(Workspace workspace)
    {
        if (workspace == null)
            throw new IllegalArgumentException("There is no such workspace!");
        return new WorkspaceDto(workspace.getWorkspaceID(), workspace.getSpaceType());
    }
}
