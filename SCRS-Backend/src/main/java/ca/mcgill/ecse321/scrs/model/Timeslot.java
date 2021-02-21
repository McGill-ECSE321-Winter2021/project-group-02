package ca.mcgill.ecse321.scrs.model;

import java.sql.Date;
import java.sql.Time;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Timeslot {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Timeslot Attributes
	private int timeSlotID;
	private Date startDate;
	private Date endDate;
	private Time startTime;
	private Time endTime;

	// Timeslot Associations
	private List<Technician> technicians;
	private Appointment appointment;
	private Workspace workspace;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Timeslot(int aTimeSlotID, Date aStartDate, Date aEndDate, Time aStartTime, Time aEndTime,
			Workspace aWorkspace) {
		timeSlotID = aTimeSlotID;
		startDate = aStartDate;
		endDate = aEndDate;
		startTime = aStartTime;
		endTime = aEndTime;
		technicians = new ArrayList<Technician>();
		boolean didAddWorkspace = setWorkspace(aWorkspace);
		if (!didAddWorkspace) {
			throw new RuntimeException(
					"Unable to create availability due to workspace. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	public Timeslot() {}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setTimeSlotID(int aTimeSlotID) {
		boolean wasSet = false;
		timeSlotID = aTimeSlotID;
		wasSet = true;
		return wasSet;
	}

	public boolean setStartDate(Date aStartDate) {
		boolean wasSet = false;
		startDate = aStartDate;
		wasSet = true;
		return wasSet;
	}

	public boolean setEndDate(Date aEndDate) {
		boolean wasSet = false;
		endDate = aEndDate;
		wasSet = true;
		return wasSet;
	}

	public boolean setStartTime(Time aStartTime) {
		boolean wasSet = false;
		startTime = aStartTime;
		wasSet = true;
		return wasSet;
	}

	public boolean setEndTime(Time aEndTime) {
		boolean wasSet = false;
		endTime = aEndTime;
		wasSet = true;
		return wasSet;
	}

	@Id
	public int getTimeSlotID() {
		return timeSlotID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	/* Code from template association_GetMany */
	public Technician getTechnicians(int index) {
		Technician aTechnician = technicians.get(index);
		return aTechnician;
	}
	
	@ManyToMany
	public List<Technician> getTechnicians() {
		return technicians;
	}
	
	public void setTechnicians(List<Technician> technician) {
		this.technicians=technician;
	}

	public int numberOfTechnicians() {
		int number = technicians.size();
		return number;
	}

	public boolean hasTechnicians() {
		boolean has = technicians.size() > 0;
		return has;
	}

	public int indexOfTechnician(Technician aTechnician) {
		int index = technicians.indexOf(aTechnician);
		return index;
	}

	/* Code from template association_GetOne */
	@ManyToOne
	public Appointment getAppointment() {
		return appointment;
	}

	public boolean hasAppointment() {
		boolean has = appointment != null;
		return has;
	}

	/* Code from template association_GetOne */
	@ManyToOne
	public Workspace getWorkspace() {
		return workspace;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfTechnician() {
		return 0;
	}

	/* Code from template association_AddManyToManyMethod */
	public boolean addTechnician(Technician aTechnician) {
		boolean wasAdded = false;
		if (technicians.contains(aTechnician)) {
			return false;
		}
		technicians.add(aTechnician);
		if (aTechnician.indexOfAvailability(this) != -1) {
			wasAdded = true;
		} else {
			wasAdded = aTechnician.addAvailability(this);
			if (!wasAdded) {
				technicians.remove(aTechnician);
			}
		}
		return wasAdded;
	}

	/* Code from template association_RemoveMany */
	public boolean removeTechnician(Technician aTechnician) {
		boolean wasRemoved = false;
		if (!technicians.contains(aTechnician)) {
			return wasRemoved;
		}

		int oldIndex = technicians.indexOf(aTechnician);
		technicians.remove(oldIndex);
		if (aTechnician.indexOfAvailability(this) == -1) {
			wasRemoved = true;
		} else {
			wasRemoved = aTechnician.removeAvailability(this);
			if (!wasRemoved) {
				technicians.add(oldIndex, aTechnician);
			}
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addTechnicianAt(Technician aTechnician, int index) {
		boolean wasAdded = false;
		if (addTechnician(aTechnician)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfTechnicians()) {
				index = numberOfTechnicians() - 1;
			}
			technicians.remove(aTechnician);
			technicians.add(index, aTechnician);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveTechnicianAt(Technician aTechnician, int index) {
		boolean wasAdded = false;
		if (technicians.contains(aTechnician)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfTechnicians()) {
				index = numberOfTechnicians() - 1;
			}
			technicians.remove(aTechnician);
			technicians.add(index, aTechnician);
			wasAdded = true;
		} else {
			wasAdded = addTechnicianAt(aTechnician, index);
		}
		return wasAdded;
	}

	/* Code from template association_SetOptionalOneToMandatoryMany */
	public boolean setAppointment(Appointment aAppointment) {
		//
		// This source of this source generation is
		// association_SetOptionalOneToMandatoryMany.jet
		// This set file assumes the generation of a maximumNumberOfXXX method does not
		// exist because
		// it's not required (No upper bound)
		//
		boolean wasSet = false;
		Appointment existingAppointment = appointment;

		if (existingAppointment == null) {
			if (aAppointment != null) {
				if (aAppointment.addTimeslot(this)) {
					existingAppointment = aAppointment;
					wasSet = true;
				}
			}
		} else if (existingAppointment != null) {
			if (aAppointment == null) {
				if (existingAppointment.minimumNumberOfTimeslots() < existingAppointment.numberOfTimeslots()) {
					existingAppointment.removeTimeslot(this);
					existingAppointment = aAppointment; // aAppointment == null
					wasSet = true;
				}
			} else {
				if (existingAppointment.minimumNumberOfTimeslots() < existingAppointment.numberOfTimeslots()) {
					existingAppointment.removeTimeslot(this);
					aAppointment.addTimeslot(this);
					existingAppointment = aAppointment;
					wasSet = true;
				}
			}
		}
		if (wasSet) {
			appointment = existingAppointment;
		}
		return wasSet;
	}

	/* Code from template association_SetOneToMany */
	public boolean setWorkspace(Workspace aWorkspace) {
		boolean wasSet = false;
		if (aWorkspace == null) {
			return wasSet;
		}

		Workspace existingWorkspace = workspace;
		workspace = aWorkspace;
		if (existingWorkspace != null && !existingWorkspace.equals(aWorkspace)) {
			existingWorkspace.removeAvailability(this);
		}
		workspace.addAvailability(this);
		wasSet = true;
		return wasSet;
	}

	public void delete() {
		ArrayList<Technician> copyOfTechnician = new ArrayList<Technician>(technicians);
		technicians.clear();
		for (Technician aTechnician : copyOfTechnician) {
			aTechnician.removeAvailability(this);
		}
		if (appointment != null) {
			if (appointment.numberOfTimeslots() <= 1) {
				appointment.delete();
			} else {
				Appointment placeholderAppointment = appointment;
				this.appointment = null;
				placeholderAppointment.removeTimeslot(this);
			}
		}
		Workspace placeholderWorkspace = workspace;
		this.workspace = null;
		if (placeholderWorkspace != null) {
			placeholderWorkspace.removeAvailability(this);
		}
	}

	public String toString() {
		return super.toString() + "[" + "timeSlotID" + ":" + getTimeSlotID() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "startDate" + "="
				+ (getStartDate() != null
						? !getStartDate().equals(this) ? getStartDate().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "endDate" + "="
				+ (getEndDate() != null
						? !getEndDate().equals(this) ? getEndDate().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "startTime" + "="
				+ (getStartTime() != null
						? !getStartTime().equals(this) ? getStartTime().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "endTime" + "="
				+ (getEndTime() != null
						? !getEndTime().equals(this) ? getEndTime().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "appointment = "
				+ (getAppointment() != null ? Integer.toHexString(System.identityHashCode(getAppointment())) : "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "workspace = "
				+ (getWorkspace() != null ? Integer.toHexString(System.identityHashCode(getWorkspace())) : "null");
	}
}