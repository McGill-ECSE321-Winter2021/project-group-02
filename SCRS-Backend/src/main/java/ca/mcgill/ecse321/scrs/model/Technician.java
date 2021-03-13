package ca.mcgill.ecse321.scrs.model;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Technician extends SCRSUser
{
    @ManyToMany
    @JoinTable(
            name = "technician_availabilities",
            joinColumns = @JoinColumn(name = "technician_id"),
            inverseJoinColumns = @JoinColumn(name = "timeslot_id")
    )
    private List<Timeslot> availabilities;

    public Technician(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs)
    {
        super(aName, aPassword, aEmail, aPhone, aScrs);
        availabilities = new ArrayList<Timeslot>();
    }

    public Technician()
    {
    }

    ;

    public List<Timeslot> getAvailabilities()
    {
        return this.availabilities;
    }

    public void setAvailabilities(List<Timeslot> av)
    {
        this.availabilities = av;
    }

    public boolean addAvailability(Timeslot aAvailability)
    {
        boolean wasAdded = false;
        if (availabilities.contains(aAvailability))
        {
            return false;
        }
        availabilities.add(aAvailability);
        if (aAvailability.indexOfTechnician(this) != -1)
        {
            wasAdded = true;
        }
        else
        {
            wasAdded = aAvailability.addTechnician(this);
            if (!wasAdded)
            {
                availabilities.remove(aAvailability);
            }
        }
        return wasAdded;
    }

    public int indexOfAvailability(Timeslot aAvailability)
    {
        int index = availabilities.indexOf(aAvailability);
        return index;
    }

    public boolean removeAvailability(Timeslot aAvailability)
    {
        boolean wasRemoved = false;
        if (!availabilities.contains(aAvailability))
        {
            return wasRemoved;
        }

        int oldIndex = availabilities.indexOf(aAvailability);
        availabilities.remove(oldIndex);
        if (aAvailability.indexOfTechnician(this) == -1)
        {
            wasRemoved = true;
        }
        else
        {
            wasRemoved = aAvailability.removeTechnician(this);
            if (!wasRemoved)
            {
                availabilities.add(oldIndex, aAvailability);
            }
        }
        return wasRemoved;
    }

    public String toString()
    {
        return super.toString();
    }
}