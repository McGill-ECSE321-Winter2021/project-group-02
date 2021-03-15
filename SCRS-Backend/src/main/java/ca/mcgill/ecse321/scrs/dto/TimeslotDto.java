package ca.mcgill.ecse321.scrs.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimeslotDto
{
    private int timeslotId;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private WorkspaceDto workspace;
    private List<TechnicianDto> technicians;

    public TimeslotDto()
    {
    }

    public TimeslotDto(int id, Date startDate, Date endDate, Time startTime, Time endTime, WorkspaceDto workspace) {
        this(id, startDate, endDate, startTime, endTime, workspace, new ArrayList<TechnicianDto>());
    }

    public TimeslotDto(int id, Date startDate, Date endDate, Time startTime, Time endTime, WorkspaceDto workspace, List<TechnicianDto> technicians)
    {
        timeslotId = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime =startTime;
        this.endTime = endTime;
        this.workspace = workspace;
        this.technicians = technicians;
    }

    public int getTimeslotId()
    {
        return timeslotId;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public Time getStartTime()
    {
        return startTime;
    }

    public Time getEndTime()
    {
        return endTime;
    }

    public WorkspaceDto getWorkspace()
    {
        return workspace;
    }

    public List<TechnicianDto> getTechnicians()
    {
        return technicians;
    }

    public void setTechnicians(List<TechnicianDto> technicians)
    {
        this.technicians = technicians;
    }
}
