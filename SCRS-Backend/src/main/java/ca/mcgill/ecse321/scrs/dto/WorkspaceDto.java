package ca.mcgill.ecse321.scrs.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkspaceDto
{
    private int workspaceId;
    private String spaceType;
    private List<TimeslotDto> availabilities;

    public WorkspaceDto()
    {
    }

    public WorkspaceDto(int id, String type)
    {
        this(id, type, new ArrayList<TimeslotDto>());
    }

    public WorkspaceDto(int id, String type, List<TimeslotDto> availabilityList)
    {
        workspaceId = id;
        spaceType = type;
        availabilities = availabilityList;
    }

    public int getWorkspaceId()
    {
        return workspaceId;
    }

    public String getSpaceType()
    {
        return spaceType;
    }

    public List<TimeslotDto> getAvailabilities()
    {
        return availabilities;
    }

    public void setAvailabilities(List<TimeslotDto> availabilityList)
    {
        availabilities = availabilityList;
    }
}
