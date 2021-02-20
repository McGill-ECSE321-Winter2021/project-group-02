package ca.mcgill.ecse321.scrs.model;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Entity;

@Entity
public class SCRS {

	//===========Workspaces============

	private List<Workspace> workspaces;

	@OneToMany(cascade = { CascadeType.ALL })
	public List<Workspace> getWorkspaces() {
		return this.workspaces;
	}

	public void setWorkspaces(List<Workspace> ws) {
		this.workspaces = ws;
	}

	//==============Users=============

	private List<User> users;

	@OneToMany(cascade = { CascadeType.ALL })
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> us) {
		this.users = us;
	}

	//==========Appointments=========

	private List<Appointment> appointments;

	@OneToMany(cascade= { CascadeType.ALL})
	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> apps) {
		this.appointments = apps;
	}

	//================================

	public SCRS() {
		workspaces = new ArrayList<Workspace>();
		users = new ArrayList<User>();
		appointments = new ArrayList<Appointment>();
	}
}