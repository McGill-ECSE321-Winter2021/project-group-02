package ca.mcgill.ecse321.scrs.model;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Technician extends SCRSUser
{
    public Technician(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs)
    {
        super(aName, aPassword, aEmail, aPhone, aScrs);
        availabilities = new ArrayList<Timeslot>();
    }

    protected Technician()
    {
        super();
        availabilities = null;
    }

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

    public Technician()
    {
    }

    public String toString()
    {
        return super.toString();
    }
}