package ca.mcgill.ecse321.scrs.model;


import java.util.*;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

@Entity
public class Workspace {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Workspace Attributes
	private int workspaceID;
	private String spaceType;

	// Workspace Associations
	private List<Timeslot> availabilities;
	private SCRS scrs;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Workspace(int aWorkspaceID, String aSpaceType, SCRS aScrs) {
		workspaceID = aWorkspaceID;
		spaceType = aSpaceType;
		availabilities = new ArrayList<Timeslot>();
		boolean didAddScrs = setScrs(aScrs);
		if (!didAddScrs) {
			throw new RuntimeException(
					"Unable to create workspace due to scrs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setWorkspaceID(int aWorkspaceID) {
		boolean wasSet = false;
		workspaceID = aWorkspaceID;
		wasSet = true;
		return wasSet;
	}

	public boolean setSpaceType(String aSpaceType) {
		boolean wasSet = false;
		spaceType = aSpaceType;
		wasSet = true;
		return wasSet;
	}

	@Id
	public int getWorkspaceID() {
		return workspaceID;
	}

	public String getSpaceType() {
		return spaceType;
	}

	/* Code from template association_GetMany */
	public Timeslot getAvailability(int index) {
		Timeslot aAvailability = availabilities.get(index);
		return aAvailability;
	}

	@OneToMany
	public List<Timeslot> getAvailabilities() {
		return availabilities;
	}
	
	public void setAvailabilities(List<Timeslot> timeslots) {
		availabilities=timeslots;
	}

	public int numberOfAvailabilities() {
		int number = availabilities.size();
		return number;
	}

	public boolean hasAvailabilities() {
		boolean has = availabilities.size() > 0;
		return has;
	}

	public int indexOfAvailability(Timeslot aAvailability) {
		int index = availabilities.indexOf(aAvailability);
		return index;
	}

	@ManyToOne /* Code from template association_GetOne */
	public SCRS getScrs() {
		return scrs;
	}
	

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfAvailabilities() {
		return 0;
	}

	/* Code from template association_AddManyToOne */
	public Timeslot addAvailability(int aTimeSlotID, Date aStartDate, Date aEndDate, Time aStartTime,
			Time aEndTime) {
		return new Timeslot(aTimeSlotID, aStartDate, aEndDate, aStartTime, aEndTime, this);
	}

	public boolean addAvailability(Timeslot aAvailability) {
		boolean wasAdded = false;
		if (availabilities.contains(aAvailability)) {
			return false;
		}
		Workspace existingWorkspace = aAvailability.getWorkspace();
		boolean isNewWorkspace = existingWorkspace != null && !this.equals(existingWorkspace);
		if (isNewWorkspace) {
			aAvailability.setWorkspace(this);
		} else {
			availabilities.add(aAvailability);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeAvailability(Timeslot aAvailability) {
		boolean wasRemoved = false;
		// Unable to remove aAvailability, as it must always have a workspace
		if (!this.equals(aAvailability.getWorkspace())) {
			availabilities.remove(aAvailability);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addAvailabilityAt(Timeslot aAvailability, int index) {
		boolean wasAdded = false;
		if (addAvailability(aAvailability)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfAvailabilities()) {
				index = numberOfAvailabilities() - 1;
			}
			availabilities.remove(aAvailability);
			availabilities.add(index, aAvailability);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveAvailabilityAt(Timeslot aAvailability, int index) {
		boolean wasAdded = false;
		if (availabilities.contains(aAvailability)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfAvailabilities()) {
				index = numberOfAvailabilities() - 1;
			}
			availabilities.remove(aAvailability);
			availabilities.add(index, aAvailability);
			wasAdded = true;
		} else {
			wasAdded = addAvailabilityAt(aAvailability, index);
		}
		return wasAdded;
	}

	/* Code from template association_SetOneToMany */
	public boolean setScrs(SCRS aScrs) {
		boolean wasSet = false;
		if (aScrs == null) {
			return wasSet;
		}

		SCRS existingScrs = scrs;
		scrs = aScrs;
		if (existingScrs != null && !existingScrs.equals(aScrs)) {
			existingScrs.removeWorkspace(this);
		}
		scrs.addWorkspace(this);
		wasSet = true;
		return wasSet;
	}

	public void delete() {
		while (availabilities.size() > 0) {
			Timeslot aAvailability = availabilities.get(availabilities.size() - 1);
			aAvailability.delete();
			availabilities.remove(aAvailability);
		}

		SCRS placeholderScrs = scrs;
		this.scrs = null;
		if (placeholderScrs != null) {
			placeholderScrs.removeWorkspace(this);
		}
	}

	public String toString() {
		return super.toString() + "[" + "workspaceID" + ":" + getWorkspaceID() + "," + "spaceType" + ":"
				+ getSpaceType() + "]" + System.getProperties().getProperty("line.separator") + "  " + "scrs = "
				+ (getScrs() != null ? Integer.toHexString(System.identityHashCode(getScrs())) : "null");
	}
}