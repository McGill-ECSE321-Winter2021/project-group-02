package ca.mcgill.ecse321.scrs.model;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Technician extends User {

	//============ID==============

	private String technicianID;

	@Id
	public String getTechnicianID() {
		return technicianID;
	}

	public void setTechnicianID(String aTechnicianID) {
		technicianID = aTechnicianID;
	}

	//=======Availabilities======
	private List<Timeslot> availabilities;

	@ManyToMany
	public List<Timeslot> getAvailabilities() {
		return this.availabilities;
	}

	public void setAvailabilities(List<Timeslot> av) {
		this.availabilities = av;
	}


	//========Constructor========

	public Technician(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, String aTechnicianID) {
		super(aName, aPassword, aEmail, aPhone, aScrs);
		technicianID = aTechnicianID;
		availabilities = new ArrayList<Timeslot>();
	}


	//=======Other Methods=======

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

	public String toString() {
		return super.toString() + "[" + "technicianID" + ":" + getTechnicianID() + "]";
	}
}