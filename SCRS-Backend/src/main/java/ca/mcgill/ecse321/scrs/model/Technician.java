package ca.mcgill.ecse321.scrs.model;

import java.util.*;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

@Entity
public class Technician extends User {

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

	public Technician(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, int id) {
		super(aName, aPassword, aEmail, aPhone, aScrs, id);
		availabilities = new ArrayList<Timeslot>();
	}

	protected Technician() {}


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
	
	 public int indexOfAvailability(Timeslot aAvailability)
	  {
	    int index = availabilities.indexOf(aAvailability);
	    return index;
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
		return super.toString();
	}
}