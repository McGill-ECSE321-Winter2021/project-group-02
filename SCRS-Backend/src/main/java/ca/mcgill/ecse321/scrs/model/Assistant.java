package ca.mcgill.ecse321.scrs.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE")
public class Assistant extends User {

	//=========Constructor=========

	public Assistant(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, int id) {
		super(aName, aPassword, aEmail, aPhone, aScrs, id);
		}

	//=======Other Methods========

	public String toString() {
		return super.toString();
	}
}