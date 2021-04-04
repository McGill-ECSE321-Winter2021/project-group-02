package ca.mcgill.ecse321.scrs.dto;

import ca.mcgill.ecse321.scrs.model.Technician;

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
    private int workspaceId;
    private ArrayList<Integer> techniciansId;

    public TimeslotDto()
    {
    }

    public TimeslotDto(int id, Date startDate, Date endDate, Time startTime, Time endTime, int workspaceId)
    {
        this(id, startDate, endDate, startTime, endTime, workspaceId, new ArrayList<Integer>());
    }

    public TimeslotDto(int id, Date startDate, Date endDate, Time startTime, Time endTime, int workspaceId, ArrayList<Integer> techniciansId)
    {
        timeslotId = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workspaceId = workspaceId;
        this.techniciansId = techniciansId;
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

    public int getWorkspaceId()
    {
        return workspaceId;
    }

    public ArrayList<Integer> getTechniciansId()
    {
        return techniciansId;
    }

    public void setTechnicians(List<Technician> technicians)
    {
        this.techniciansId = new ArrayList<Integer>();
        if (technicians != null)
        {
            for (Technician technician : technicians)
            {
                techniciansId.add(technician.getScrsUserId());
            }
        }
    }
}
