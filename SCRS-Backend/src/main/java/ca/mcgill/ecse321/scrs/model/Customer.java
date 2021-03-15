package ca.mcgill.ecse321.scrs.model;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Customer extends SCRSUser
{
    // Customer Associations
    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments;

    public Customer(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs)
    {
        super(aName, aPassword, aEmail, aPhone, aScrs);
        appointments = new ArrayList<Appointment>();
    }

    protected Customer()
    {
        super();
        appointments = null;
    }

    public static int minimumNumberOfAppointments()
    {
        return 0;
    }

    public Appointment getAppointment(int index)
    {
        return appointments.get(index);
    }

    public List<Appointment> getAppointments()
    {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments)
    {
        this.appointments = appointments;
    }

    public int numberOfAppointments()
    {
        return appointments.size();
    }

    public boolean hasAppointments()
    {
        return appointments.size() > 0;
    }

    public int indexOfAppointment(Appointment aAppointment)
    {
        return appointments.indexOf(aAppointment);
    }

    public Appointment addAppointment(ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType aAppointmentType, String aService, String aNote,
                                      int aRating, String aFeedback, boolean aPaid, SCRS aScrs, Timeslot... allTimeslots)
    {
        return new Appointment(aAppointmentType, aService, aNote, aRating, aFeedback, aPaid, this,
                aScrs, allTimeslots);
    }

    public boolean addAppointment(Appointment aAppointment)
    {
        boolean wasAdded = false;
        if (appointments.contains(aAppointment))
        {
            return false;
        }
        Customer existingCustomer = aAppointment.getCustomer();
        boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
        if (isNewCustomer)
        {
            aAppointment.setCustomer(this);
        }
        else
        {
            appointments.add(aAppointment);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeAppointment(Appointment aAppointment)
    {
        boolean wasRemoved = false;
        // Unable to remove aAppointment, as it must always have a customer
        if (!this.equals(aAppointment.getCustomer()))
        {
            appointments.remove(aAppointment);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addAppointmentAt(Appointment aAppointment, int index)
    {
        boolean wasAdded = false;
        if (addAppointment(aAppointment))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfAppointments())
            {
                index = numberOfAppointments() - 1;
            }
            appointments.remove(aAppointment);
            appointments.add(index, aAppointment);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
    {
        boolean wasAdded = false;
        if (appointments.contains(aAppointment))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfAppointments())
            {
                index = numberOfAppointments() - 1;
            }
            appointments.remove(aAppointment);
            appointments.add(index, aAppointment);
            wasAdded = true;
        }
        else
        {
            wasAdded = addAppointmentAt(aAppointment, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        for (int i = appointments.size(); i > 0; i--)
        {
            Appointment aAppointment = appointments.get(i - 1);
            aAppointment.delete();
        }
        super.delete();
    }

    public String toString()
    {
        return super.toString();
    }
}