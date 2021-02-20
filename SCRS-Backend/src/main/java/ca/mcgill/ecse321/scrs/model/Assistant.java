package ca.mcgill.ecse321.scrs.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Assistant extends User {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Assistant Attributes
	private String assistantID;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Assistant(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, String aAssistantID) {
		super(aName, aPassword, aEmail, aPhone, aScrs);
		assistantID = aAssistantID;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setAssistantID(String aAssistantID) {
		boolean wasSet = false;
		assistantID = aAssistantID;
		wasSet = true;
		return wasSet;
	}
	
	@Id
	public String getAssistantID() {
		return assistantID;
	}

	public void delete() {
		super.delete();
	}

	public String toString() {
		return super.toString() + "[" + "assistantID" + ":" + getAssistantID() + "]";
	}
}