package ca.mcgill.ecse321.scrs.model;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.sql.Time;
import java.util.*;
import javax.persistence.*;

@Entity
public class Timeslot
{

    // Timeslot Attributes
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int timeSlotID;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;

    // Timeslot Associations
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Technician> technicians;
    @ManyToOne
    private Workspace workspace;

    public Timeslot(Date aStartDate, Date aEndDate, Time aStartTime, Time aEndTime,
                    Workspace aWorkspace)
    {
        startDate = aStartDate;
        endDate = aEndDate;
        startTime = aStartTime;
        endTime = aEndTime;
        technicians = new ArrayList<Technician>();
        boolean didAddWorkspace = setWorkspace(aWorkspace);
        if (!didAddWorkspace)
        {
            throw new RuntimeException(
                    "Unable to create availability due to workspace. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    public Timeslot()
    {
    }

    ;

    public static int minimumNumberOfTechnician()
    {
        return 0;
    }

    public boolean setTimeSlotID(int aTimeSlotID)
    {
        timeSlotID = aTimeSlotID;
        return true;
    }

    public boolean setStartDate(Date aStartDate)
    {
        startDate = aStartDate;
        return true;
    }

    public boolean setEndDate(Date aEndDate)
    {
        endDate = aEndDate;
        return true;
    }

    public boolean setStartTime(Time aStartTime)
    {
        startTime = aStartTime;
        return true;
    }

    public boolean setEndTime(Time aEndTime)
    {
        endTime = aEndTime;
        return true;
    }

    public int getTimeSlotID()
    {
        return timeSlotID;
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

    public Technician getTechnicians(int index)
    {
        Technician aTechnician = technicians.get(index);
        return aTechnician;
    }

    public List<Technician> getTechnicians()
    {
        return technicians;
    }

    public void setTechnicians(List<Technician> technician)
    {
        this.technicians = technician;
    }

    public int numberOfTechnicians()
    {
        return technicians.size();
    }

    public boolean hasTechnicians()
    {
        return technicians.size() > 0;
    }

    public int indexOfTechnician(Technician aTechnician)
    {
        return technicians.indexOf(aTechnician);
    }

    public Workspace getWorkspace()
    {
        return workspace;
    }

    /* Code from template association_AddManyToManyMethod */
    public boolean addTechnician(Technician aTechnician)
    {
        if (technicians.contains(aTechnician))
        {
            return false;
        }
        technicians.add(aTechnician);
        return true;
    }

    /* Code from template association_RemoveMany */
    public boolean removeTechnician(Technician aTechnician)
    {
        if (!technicians.contains(aTechnician))
        {
            return false;
        }

        technicians.remove(aTechnician);
        return true;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addTechnicianAt(Technician aTechnician, int index)
    {
        boolean wasAdded = false;
        if (addTechnician(aTechnician))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfTechnicians())
            {
                index = numberOfTechnicians() - 1;
            }
            technicians.remove(aTechnician);
            technicians.add(index, aTechnician);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveTechnicianAt(Technician aTechnician, int index)
    {
        boolean wasAdded = false;
        if (technicians.contains(aTechnician))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfTechnicians())
            {
                index = numberOfTechnicians() - 1;
            }
            technicians.remove(aTechnician);
            technicians.add(index, aTechnician);
            wasAdded = true;
        }
        else
        {
            wasAdded = addTechnicianAt(aTechnician, index);
        }
        return wasAdded;
    }


    /* Code from template association_SetOneToMany */
    public boolean setWorkspace(Workspace aWorkspace)
    {
        if (aWorkspace == null)
        {
            return false;
        }

        Workspace existingWorkspace = workspace;
        workspace = aWorkspace;
        if (existingWorkspace != null && !existingWorkspace.equals(aWorkspace))
        {
            existingWorkspace.removeAvailability(this);
        }
        workspace.addAvailability(this);
        return true;
    }

    public void delete()
    {
        technicians.clear();
        Workspace placeholderWorkspace = workspace;
        this.workspace = null;
        if (placeholderWorkspace != null)
        {
            placeholderWorkspace.removeAvailability(this);
        }
    }

    public String toString()
    {
        return super.toString() + "[" + "timeSlotID" + ":" + getTimeSlotID() + "]"
                + System.getProperties().getProperty("line.separator") + "  " + "startDate" + "="
                + (getStartDate() != null
                ? !getStartDate().equals(this.startDate) ? getStartDate().toString().replaceAll("  ", "    ") : "this"
                : "null")
                + System.getProperties().getProperty("line.separator") + "  " + "endDate" + "="
                + (getEndDate() != null
                ? !getEndDate().equals(this.endDate) ? getEndDate().toString().replaceAll("  ", "    ") : "this"
                : "null")
                + System.getProperties().getProperty("line.separator") + "  " + "startTime" + "="
                + (getStartTime() != null
                ? !getStartTime().equals(this.startTime) ? getStartTime().toString().replaceAll("  ", "    ") : "this"
                : "null")
                + System.getProperties().getProperty("line.separator") + "  " + "endTime" + "="
                + (getEndTime() != null
                ? !getEndTime().equals(this.endTime) ? getEndTime().toString().replaceAll("  ", "    ") : "this"
                : "null")
                + System.getProperties().getProperty("line.separator") + "  " + "workspace = "
                + (getWorkspace() != null ? Integer.toHexString(System.identityHashCode(getWorkspace())) : "null");
    }
}