package ca.mcgill.ecse321.scrs.model;

import javax.persistence.Entity;


@Entity
public class Assistant extends SCRSUser
{

	//=========Constructor=========

	public Assistant(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, int id) {
		super(aName, aPassword, aEmail, aPhone, aScrs, id);
	}

	protected Assistant() {}

	//=======Other Methods========

	public String toString() {
		return super.toString();
	}
}