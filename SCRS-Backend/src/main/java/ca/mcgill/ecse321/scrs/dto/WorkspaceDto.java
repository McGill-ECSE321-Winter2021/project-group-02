package ca.mcgill.ecse321.scrs.dto;

import ca.mcgill.ecse321.scrs.model.Timeslot;

import java.util.ArrayList;
import java.util.List;

public class WorkspaceDto
{
    private int workspaceId;
    private String spaceName;
    private ArrayList<Integer> timeslotsId;

    public WorkspaceDto()
    {
    }

    public WorkspaceDto(int id, String name)
    {
        this(id, name, new ArrayList<Integer>());
    }

    public WorkspaceDto(int id, String name, ArrayList<Integer> timeslotsId)
    {
        workspaceId = id;
        spaceName = name;
        this.timeslotsId = timeslotsId;
    }

    public int getWorkspaceId()
    {
        return workspaceId;
    }

    public String getSpaceName()
    {
        return spaceName;
    }

    public List<Integer> getTimeslotsId()
    {
        return timeslotsId;
    }

    public void setTimeslotsId(List<Timeslot> availabilities)
    {
        this.timeslotsId = new ArrayList<Integer>();
        if (availabilities != null)
        {
            for (Timeslot timeslot : availabilities)
            {
                timeslotsId.add(timeslot.getTimeSlotID());
            }
        }
    }
}
