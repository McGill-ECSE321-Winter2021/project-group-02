package ca.mcgill.ecse321.scrs.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SCRS
{
    // SCRS Associations
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int scrsId;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "scrs")
    private List<Workspace> workspaces;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "scrs")
    private List<SCRSUser> scrsUsers;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "scrs")
    private List<Appointment> appointments;

    public SCRS()
    {
        workspaces = new ArrayList<Workspace>();
        scrsUsers = new ArrayList<SCRSUser>();
        appointments = new ArrayList<Appointment>();
    }

    public static int minimumNumberOfWorkspaces()
    {
        return 0;
    }

    public static int minimumNumberOfUsers()
    {
        return 0;
    }

    public static int minimumNumberOfAppointments()
    {
        return 0;
    }

    public int getScrsId()
    {
        return this.scrsId;
    }

    public void setScrsId(int scrsId)
    {
        this.scrsId = scrsId;
    }

    public Workspace getWorkspace(int index)
    {
        return workspaces.get(index);
    }

    public List<Workspace> getWorkspaces()
    {
        return this.workspaces;
    }

    public void setWorkspaces(List<Workspace> ws)
    {
        this.workspaces = ws;
    }

    public int numberOfWorkspaces()
    {
        return workspaces.size();
    }

    public boolean hasWorkspaces()
    {
        return workspaces.size() > 0;
    }

    public int indexOfWorkspace(Workspace aWorkspace)
    {
        return workspaces.indexOf(aWorkspace);
    }

    public SCRSUser getUser(int index)
    {
        return scrsUsers.get(index);
    }

    public List<SCRSUser> getSCRSUsers()
    {
        return this.scrsUsers;
    }

    public void setSCRSUsers(List<SCRSUser> us)
    {
        this.scrsUsers = us;
    }

    public int numberOfUsers()
    {
        return scrsUsers.size();
    }

    public boolean hasUsers()
    {
        return scrsUsers.size() > 0;
    }

    public int indexOfUser(SCRSUser aSCRSUser)
    {
        return scrsUsers.indexOf(aSCRSUser);
    }

    public Appointment getAppointment(int index)
    {
        return appointments.get(index);
    }

    public List<Appointment> getAppointments()
    {
        return this.appointments;
    }

    public void setAppointments(List<Appointment> apps)
    {
        this.appointments = apps;
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

    /* Code from template association_AddManyToOne */
    public Workspace addWorkspace(String aSpaceType)
    {
        return new Workspace(aSpaceType, this);
    }

    public boolean addWorkspace(Workspace aWorkspace)
    {
        boolean wasAdded = false;
        if (workspaces.contains(aWorkspace))
        {
            return false;
        }
        SCRS existingScrs = aWorkspace.getScrs();
        boolean isNewScrs = existingScrs != null && !this.equals(existingScrs);
        if (isNewScrs)
        {
            aWorkspace.setScrs(this);
        } else
        {
            workspaces.add(aWorkspace);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeWorkspace(Workspace aWorkspace)
    {
        boolean wasRemoved = false;
        // Unable to remove aWorkspace, as it must always have a scrs
        if (!this.equals(aWorkspace.getScrs()))
        {
            workspaces.remove(aWorkspace);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addWorkspaceAt(Workspace aWorkspace, int index)
    {
        boolean wasAdded = false;
        if (addWorkspace(aWorkspace))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfWorkspaces())
            {
                index = numberOfWorkspaces() - 1;
            }
            workspaces.remove(aWorkspace);
            workspaces.add(index, aWorkspace);
            wasAdded = true;
        }
        return wasAdded;
    }
    /* Code from template association_AddManyToOne */

    public boolean addOrMoveWorkspaceAt(Workspace aWorkspace, int index)
    {
        boolean wasAdded = false;
        if (workspaces.contains(aWorkspace))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfWorkspaces())
            {
                index = numberOfWorkspaces() - 1;
            }
            workspaces.remove(aWorkspace);
            workspaces.add(index, aWorkspace);
            wasAdded = true;
        } else
        {
            wasAdded = addWorkspaceAt(aWorkspace, index);
        }
        return wasAdded;
    }

    public boolean addUser(SCRSUser aSCRSUser)
    {
        boolean wasAdded = false;
        if (scrsUsers.contains(aSCRSUser))
        {
            return false;
        }
        SCRS existingScrs = aSCRSUser.getScrs();
        boolean isNewScrs = existingScrs != null && !this.equals(existingScrs);
        if (isNewScrs)
        {
            aSCRSUser.setScrs(this);
        } else
        {
            scrsUsers.add(aSCRSUser);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeUser(SCRSUser aSCRSUser)
    {
        boolean wasRemoved = false;
        // Unable to remove aUser, as it must always have a scrs
        if (!this.equals(aSCRSUser.getScrs()))
        {
            scrsUsers.remove(aSCRSUser);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addUserAt(SCRSUser aSCRSUser, int index)
    {
        boolean wasAdded = false;
        if (addUser(aSCRSUser))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfUsers())
            {
                index = numberOfUsers() - 1;
            }
            scrsUsers.remove(aSCRSUser);
            scrsUsers.add(index, aSCRSUser);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveUserAt(SCRSUser aSCRSUser, int index)
    {
        boolean wasAdded = false;
        if (scrsUsers.contains(aSCRSUser))
        {
            if (index < 0)
            {
                index = 0;
            }
            if (index > numberOfUsers())
            {
                index = numberOfUsers() - 1;
            }
            scrsUsers.remove(aSCRSUser);
            scrsUsers.add(index, aSCRSUser);
            wasAdded = true;
        } else
        {
            wasAdded = addUserAt(aSCRSUser, index);
        }
        return wasAdded;
    }

    /* Code from template association_AddManyToOne */
    public Appointment addAppointment(ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType aAppointmentType, String aService, String aNote,
                                      int aRating, String aFeedback, boolean aPaid, Customer aCustomer, Timeslot... allTimeslots)
    {
        return new Appointment(aAppointmentType, aService, aNote, aRating, aFeedback, aPaid, aCustomer,
                this, allTimeslots);
    }

    public boolean addAppointment(Appointment aAppointment)
    {
        boolean wasAdded = false;
        if (appointments.contains(aAppointment))
        {
            return false;
        }
        SCRS existingScrs = aAppointment.getScrs();
        boolean isNewScrs = existingScrs != null && !this.equals(existingScrs);
        if (isNewScrs)
        {
            aAppointment.setScrs(this);
        } else
        {
            appointments.add(aAppointment);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeAppointment(Appointment aAppointment)
    {
        boolean wasRemoved = false;
        // Unable to remove aAppointment, as it must always have a scrs
        if (!this.equals(aAppointment.getScrs()))
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
        } else
        {
            wasAdded = addAppointmentAt(aAppointment, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        while (workspaces.size() > 0)
        {
            Workspace aWorkspace = workspaces.get(workspaces.size() - 1);
            aWorkspace.delete();
            workspaces.remove(aWorkspace);
        }

        while (scrsUsers.size() > 0)
        {
            SCRSUser aSCRSUser = scrsUsers.get(scrsUsers.size() - 1);
            aSCRSUser.delete();
            scrsUsers.remove(aSCRSUser);
        }

        while (appointments.size() > 0)
        {
            Appointment aAppointment = appointments.get(appointments.size() - 1);
            aAppointment.delete();
            appointments.remove(aAppointment);
        }
    }
}