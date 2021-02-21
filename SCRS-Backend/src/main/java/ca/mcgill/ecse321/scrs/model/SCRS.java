package ca.mcgill.ecse321.scrs.model;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Entity;

@Entity
public class SCRS {
	@Id
	private int scrsId;

	// SCRS Associations
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy="scrs")
	private List<Workspace> workspaces;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy="scrs")
	private List<SCRSUser> SCRSUsers;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy="scrs")
	private List<Appointment> appointments;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public SCRS() {
		workspaces = new ArrayList<Workspace>();
		SCRSUsers = new ArrayList<SCRSUser>();
		appointments = new ArrayList<Appointment>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public int getScrsId() {
		return this.scrsId;
	}

	public void setScrsId(int scrsId) {
		this.scrsId = scrsId;
	}

	/* Code from template association_GetMany */
	public Workspace getWorkspace(int index) {
		Workspace aWorkspace = workspaces.get(index);
		return aWorkspace;
	}

	public List<Workspace> getWorkspaces() {
		return this.workspaces;
	}

	public void setWorkspaces(List<Workspace> ws) {
		this.workspaces = ws;
	}
	
	public int numberOfWorkspaces() {
		int number = workspaces.size();
		return number;
	}

	public boolean hasWorkspaces() {
		boolean has = workspaces.size() > 0;
		return has;
	}

	public int indexOfWorkspace(Workspace aWorkspace) {
		int index = workspaces.indexOf(aWorkspace);
		return index;
	}

	/* Code from template association_GetMany */
	public SCRSUser getUser(int index) {
		SCRSUser aSCRSUser = SCRSUsers.get(index);
		return aSCRSUser;
	}

	public List<SCRSUser> getSCRSUsers() {
		return this.SCRSUsers;
	}

	public void setSCRSUsers(List<SCRSUser> us) {
		this.SCRSUsers = us;
	}

	public int numberOfUsers() {
		int number = SCRSUsers.size();
		return number;
	}

	public boolean hasUsers() {
		boolean has = SCRSUsers.size() > 0;
		return has;
	}

	public int indexOfUser(SCRSUser aSCRSUser) {
		int index = SCRSUsers.indexOf(aSCRSUser);
		return index;
	}

	/* Code from template association_GetMany */
	public Appointment getAppointment(int index) {
		Appointment aAppointment = appointments.get(index);
		return aAppointment;
	}

	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> apps) {
		this.appointments = apps;
	}

	public int numberOfAppointments() {
		int number = appointments.size();
		return number;
	}

	public boolean hasAppointments() {
		boolean has = appointments.size() > 0;
		return has;
	}

	public int indexOfAppointment(Appointment aAppointment) {
		int index = appointments.indexOf(aAppointment);
		return index;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfWorkspaces() {
		return 0;
	}

	/* Code from template association_AddManyToOne */
	public Workspace addWorkspace(int aWorkspaceID, String aSpaceType) {
		return new Workspace(aWorkspaceID, aSpaceType, this);
	}

	public boolean addWorkspace(Workspace aWorkspace) {
		boolean wasAdded = false;
		if (workspaces.contains(aWorkspace)) {
			return false;
		}
		SCRS existingScrs = aWorkspace.getScrs();
		boolean isNewScrs = existingScrs != null && !this.equals(existingScrs);
		if (isNewScrs) {
			aWorkspace.setScrs(this);
		} else {
			workspaces.add(aWorkspace);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeWorkspace(Workspace aWorkspace) {
		boolean wasRemoved = false;
		// Unable to remove aWorkspace, as it must always have a scrs
		if (!this.equals(aWorkspace.getScrs())) {
			workspaces.remove(aWorkspace);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addWorkspaceAt(Workspace aWorkspace, int index) {
		boolean wasAdded = false;
		if (addWorkspace(aWorkspace)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfWorkspaces()) {
				index = numberOfWorkspaces() - 1;
			}
			workspaces.remove(aWorkspace);
			workspaces.add(index, aWorkspace);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveWorkspaceAt(Workspace aWorkspace, int index) {
		boolean wasAdded = false;
		if (workspaces.contains(aWorkspace)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfWorkspaces()) {
				index = numberOfWorkspaces() - 1;
			}
			workspaces.remove(aWorkspace);
			workspaces.add(index, aWorkspace);
			wasAdded = true;
		} else {
			wasAdded = addWorkspaceAt(aWorkspace, index);
		}
		return wasAdded;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfUsers() {
		return 0;
	}
	/* Code from template association_AddManyToOne */

	public boolean addUser(SCRSUser aSCRSUser) {
		boolean wasAdded = false;
		if (SCRSUsers.contains(aSCRSUser)) {
			return false;
		}
		SCRS existingScrs = aSCRSUser.getScrs();
		boolean isNewScrs = existingScrs != null && !this.equals(existingScrs);
		if (isNewScrs) {
			aSCRSUser.setScrs(this);
		} else {
			SCRSUsers.add(aSCRSUser);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeUser(SCRSUser aSCRSUser) {
		boolean wasRemoved = false;
		// Unable to remove aUser, as it must always have a scrs
		if (!this.equals(aSCRSUser.getScrs())) {
			SCRSUsers.remove(aSCRSUser);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addUserAt(SCRSUser aSCRSUser, int index) {
		boolean wasAdded = false;
		if (addUser(aSCRSUser)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfUsers()) {
				index = numberOfUsers() - 1;
			}
			SCRSUsers.remove(aSCRSUser);
			SCRSUsers.add(index, aSCRSUser);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveUserAt(SCRSUser aSCRSUser, int index) {
		boolean wasAdded = false;
		if (SCRSUsers.contains(aSCRSUser)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfUsers()) {
				index = numberOfUsers() - 1;
			}
			SCRSUsers.remove(aSCRSUser);
			SCRSUsers.add(index, aSCRSUser);
			wasAdded = true;
		} else {
			wasAdded = addUserAt(aSCRSUser, index);
		}
		return wasAdded;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfAppointments() {
		return 0;
	}

	/* Code from template association_AddManyToOne */
	public Appointment addAppointment(int aAppointmentID,
			ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType aAppointmentType, String aService, String aNote,
			int aRating, String aFeedback, boolean aPaid, Customer aCustomer, Timeslot... allTimeslots) {
		return new Appointment(aAppointmentID, aAppointmentType, aService, aNote, aRating, aFeedback, aPaid, aCustomer,
				this, allTimeslots);
	}

	public boolean addAppointment(Appointment aAppointment) {
		boolean wasAdded = false;
		if (appointments.contains(aAppointment)) {
			return false;
		}
		SCRS existingScrs = aAppointment.getScrs();
		boolean isNewScrs = existingScrs != null && !this.equals(existingScrs);
		if (isNewScrs) {
			aAppointment.setScrs(this);
		} else {
			appointments.add(aAppointment);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeAppointment(Appointment aAppointment) {
		boolean wasRemoved = false;
		// Unable to remove aAppointment, as it must always have a scrs
		if (!this.equals(aAppointment.getScrs())) {
			appointments.remove(aAppointment);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addAppointmentAt(Appointment aAppointment, int index) {
		boolean wasAdded = false;
		if (addAppointment(aAppointment)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfAppointments()) {
				index = numberOfAppointments() - 1;
			}
			appointments.remove(aAppointment);
			appointments.add(index, aAppointment);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index) {
		boolean wasAdded = false;
		if (appointments.contains(aAppointment)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfAppointments()) {
				index = numberOfAppointments() - 1;
			}
			appointments.remove(aAppointment);
			appointments.add(index, aAppointment);
			wasAdded = true;
		} else {
			wasAdded = addAppointmentAt(aAppointment, index);
		}
		return wasAdded;
	}

	public void delete() {
		while (workspaces.size() > 0) {
			Workspace aWorkspace = workspaces.get(workspaces.size() - 1);
			aWorkspace.delete();
			workspaces.remove(aWorkspace);
		}

		while (SCRSUsers.size() > 0) {
			SCRSUser aSCRSUser = SCRSUsers.get(SCRSUsers.size() - 1);
			aSCRSUser.delete();
			SCRSUsers.remove(aSCRSUser);
		}

		while (appointments.size() > 0) {
			Appointment aAppointment = appointments.get(appointments.size() - 1);
			aAppointment.delete();
			appointments.remove(aAppointment);
		}

	}

}