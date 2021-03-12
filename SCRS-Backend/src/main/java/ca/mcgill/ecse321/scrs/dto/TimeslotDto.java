package ca.mcgill.ecse321.scrs.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

public class TimeslotDto
{
    private int timeslotId;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private AppointmentDto appointment;
    private WorkspaceDto workspace;
    private List<TechnicianDto> technicians;

    public TimeslotDto()
    {
    }

    @SuppressWarnings("unchecked")
    public TimeslotDto(int id, Date startDate, Date endDate, Time startTime, Time endTime, WorkspaceDto workspace) {
        this(id, startDate, endDate, startTime, endTime, null, workspace, Collections.EMPTY_LIST);
    }

    @SuppressWarnings("unchecked")
    public TimeslotDto(int id, Date startDate, Date endDate, Time startTime, Time endTime, WorkspaceDto workspace, List<TechnicianDto> technicians) {
        this(id, startDate, endDate, startTime, endTime, null, workspace, technicians);
    }

    public TimeslotDto(int id, Date startDate, Date endDate, Time startTime, Time endTime, AppointmentDto appointment, WorkspaceDto workspace, List<TechnicianDto> technicians)
    {
        timeslotId = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime =startTime;
        this.endTime = endTime;
        this.appointment = appointment;
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

    public AppointmentDto getAppointment()
    {
        return appointment;
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
