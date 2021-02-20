package ca.mcgill.ecse321.scrs.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Assistant extends User {

	//=============ID==============
	private String assistantID;

	@Id
	public String getAssistantID() {
		return assistantID;
	}

	public void setAssistantID(String aAssistantID) {
		assistantID = aAssistantID;
	}

	//=========Constructor=========

	public Assistant(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, String aAssistantID) {
		super(aName, aPassword, aEmail, aPhone, aScrs);
		assistantID = aAssistantID;
	}

	//=======Other Methods========

	public String toString() {
		return super.toString() + "[" + "assistantID" + ":" + getAssistantID() + "]";
	}
}