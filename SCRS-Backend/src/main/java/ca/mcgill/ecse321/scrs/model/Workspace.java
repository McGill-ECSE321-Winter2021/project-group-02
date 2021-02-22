package ca.mcgill.ecse321.scrs.model;


import org.hibernate.annotations.GenericGenerator;

import java.util.*;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

@Entity
public class Workspace
{
    // Workspace Attributes
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int workspaceID;
    private String spaceType;

    // Workspace Associations
    @OneToMany(mappedBy = "workspace")
    private List<Timeslot> availabilities;
    @ManyToOne
    private SCRS scrs;

    public Workspace(int aWorkspaceID, String aSpaceType, SCRS aScrs)
    {
        workspaceID = aWorkspaceID;
        spaceType = aSpaceType;
        availabilities = new ArrayList<Timeslot>();
        boolean didAddScrs = setScrs(aScrs);
        if (!didAddScrs)
        {
            throw new RuntimeException(
                    "Unable to create workspace due to scrs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    public Workspace(String aSpaceType, SCRS aScrs)
    {
        spaceType = aSpaceType;
        availabilities = new ArrayList<Timeslot>();
        boolean didAddScrs = setScrs(aScrs);
        if (!didAddScrs)
        {
            throw new RuntimeException(
                    "Unable to create workspace due to scrs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    protected Workspace() {};

    public static int minimumNumberOfAvailabilities()
    {
        return 0;
    }

    public boolean setWorkspaceID(int aWorkspaceID)
    {
        workspaceID = aWorkspaceID;
        return true;
    }

    public boolean setSpaceType(String aSpaceType)
    {
        spaceType = aSpaceType;
        return true;
    }

    public int getWorkspaceID()
    {
        return workspaceID;
    }

    public String getSpaceType()
    {
        return spaceType;
    }

    public Timeslot getAvailability(int index)
    {
        return availabilities.get(index);
    }

    public List<Timeslot> getAvailabilities()
    {
        return availabilities;
    }

    public void setAvailabilities(List<Timeslot> timeslots)
    {
        availabilities = timeslots;
    }

    public int numberOfAvailabilities()
    {
        return availabilities.size();
    }

    public boolean hasAvailabilities()
    {
        return availabilities.size() > 0;
    }

    public int indexOfAvailability(Timeslot aAvailability)
    {
        return availabilities.indexOf(aAvailability);
    }

    public SCRS getScrs()
    {
        return scrs;
    }

    public Timeslot addAvailability(int aTimeSlotID, Date aStartDate, Date aEndDate, Time aStartTime,
                                    Time aEndTime)
    {
        return new Timeslot(aTimeSlotID, aStartDate, aEndDate, aStartTime, aEndTime, this);
    }

    public boolean addAvailability(Timeslot aAvailability)
    {
        boolean wasAdded = false;
        if (availabilities.contains(aAvailability))
        {
            return false;
        }
        Workspace existingWorkspace = aAvailability.getWorkspace();
        boolean isNewWorkspace = existingWorkspace != null && !this.equals(existingWorkspace);
        if (isNewWorkspace)
        {
            aAvailability.setWorkspace(this);
        }
        else
        {
            availabilities.add(aAvailability);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeAvailability(Timeslot aAvailability)
    {
        boolean wasRemoved = false;
        // Unable to remove aAvailability, as it must always have a workspace
        if (!this.equals(aAvailability.getWorkspace()))
        {
            availabilities.remove(aAvailability);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public boolean addAvailabilityAt(Timeslot aAvailability, int index)
    {
        boolean wasAdded = false;
        if (addAvailability(aAvailability))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfAvailabilities())
            {
                index = numberOfAvailabilities() - 1;
            }
            availabilities.remove(aAvailability);
            availabilities.add(index, aAvailability);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveAvailabilityAt(Timeslot aAvailability, int index)
    {
        boolean wasAdded = false;
        if (availabilities.contains(aAvailability))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfAvailabilities())
            {
                index = numberOfAvailabilities() - 1;
            }
            availabilities.remove(aAvailability);
            availabilities.add(index, aAvailability);
            wasAdded = true;
        }
        else
        {
            wasAdded = addAvailabilityAt(aAvailability, index);
        }
        return wasAdded;
    }

    /* Code from template association_SetOneToMany */
    public boolean setScrs(SCRS aScrs)
    {
        boolean wasSet = false;
        if (aScrs == null)
        {
            return wasSet;
        }

        SCRS existingScrs = scrs;
        scrs = aScrs;
        if (existingScrs != null && !existingScrs.equals(aScrs))
        {
            existingScrs.removeWorkspace(this);
        }
        scrs.addWorkspace(this);
        wasSet = true;
        return wasSet;
    }

    public void delete()
    {
        while (availabilities.size() > 0)
        {
            Timeslot aAvailability = availabilities.get(availabilities.size() - 1);
            aAvailability.delete();
            availabilities.remove(aAvailability);
        }

        SCRS placeholderScrs = scrs;
        this.scrs = null;
        if (placeholderScrs != null)
        {
            placeholderScrs.removeWorkspace(this);
        }
    }

    public String toString()
    {
        return super.toString() + "[" + "workspaceID" + ":" + getWorkspaceID() + "," + "spaceType" + ":"
                + getSpaceType() + "]" + System.getProperties().getProperty("line.separator") + "  " + "scrs = "
                + (getScrs() != null ? Integer.toHexString(System.identityHashCode(getScrs())) : "null");
    }
}