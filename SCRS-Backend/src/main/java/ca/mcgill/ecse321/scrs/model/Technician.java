package ca.mcgill.ecse321.scrs.model;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Technician extends User {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Technician Attributes
	private String technicianID;

	// Technician Associations
	private List<Timeslot> availabilities;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Technician(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, String aTechnicianID) {
		super(aName, aPassword, aEmail, aPhone, aScrs);
		technicianID = aTechnicianID;
		availabilities = new ArrayList<Timeslot>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setTechnicianID(String aTechnicianID) {
		boolean wasSet = false;
		technicianID = aTechnicianID;
		wasSet = true;
		return wasSet;
	}
	
	@Id
	public String getTechnicianID() {
		return technicianID;
	}

	/* Code from template association_GetMany */
	public Timeslot getAvailability(int index) {
		Timeslot aAvailability = availabilities.get(index);
		return aAvailability;
	}

	public List<Timeslot> getAvailabilities() {
		List<Timeslot> newAvailabilities = Collections.unmodifiableList(availabilities);
		return newAvailabilities;
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

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfAvailabilities() {
		return 0;
	}

	/* Code from template association_AddManyToManyMethod */
	public boolean addAvailability(Timeslot aAvailability) {
		boolean wasAdded = false;
		if (availabilities.contains(aAvailability)) {
			return false;
		}
		availabilities.add(aAvailability);
		if (aAvailability.indexOfTechnician(this) != -1) {
			wasAdded = true;
		} else {
			wasAdded = aAvailability.addTechnician(this);
			if (!wasAdded) {
				availabilities.remove(aAvailability);
			}
		}
		return wasAdded;
	}

	/* Code from template association_RemoveMany */
	public boolean removeAvailability(Timeslot aAvailability) {
		boolean wasRemoved = false;
		if (!availabilities.contains(aAvailability)) {
			return wasRemoved;
		}

		int oldIndex = availabilities.indexOf(aAvailability);
		availabilities.remove(oldIndex);
		if (aAvailability.indexOfTechnician(this) == -1) {
			wasRemoved = true;
		} else {
			wasRemoved = aAvailability.removeTechnician(this);
			if (!wasRemoved) {
				availabilities.add(oldIndex, aAvailability);
			}
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

	public void delete() {
		ArrayList<Timeslot> copyOfAvailabilities = new ArrayList<Timeslot>(availabilities);
		availabilities.clear();
		for (Timeslot aAvailability : copyOfAvailabilities) {
			aAvailability.removeTechnician(this);
		}
		super.delete();
	}

	public String toString() {
		return super.toString() + "[" + "technicianID" + ":" + getTechnicianID() + "]";
	}
}