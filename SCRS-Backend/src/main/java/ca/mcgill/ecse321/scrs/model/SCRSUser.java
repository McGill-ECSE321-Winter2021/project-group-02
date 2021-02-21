package ca.mcgill.ecse321.scrs.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorColumn(name="TYPE")
public abstract class SCRSUser
{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Account Attributes
	private String name;
	private String password;
	private String email;
	private String phone;

	// Account Associations
	@ManyToOne
	private SCRS scrs;
	@Id
	private int scrsUserId;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public SCRSUser(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, int aScrsUserId) {
		name = aName;
		password = aPassword;
		email = aEmail;
		phone = aPhone;
		scrsUserId = aScrsUserId;
		boolean didAddScrs = setScrs(aScrs);
		if (!didAddScrs) {
			throw new RuntimeException(
					"Unable to create user due to scrs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	protected SCRSUser() {}

	// ------------------------
	// INTERFACE
	// ------------------------

	public int getScrsUserId() {
		return this.scrsUserId;
	}

	public void setScrsUserId(int UserId) {
		this.scrsUserId = UserId;
	}

	public boolean setName(String aName) {
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	public boolean setPassword(String aPassword) {
		boolean wasSet = false;
		password = aPassword;
		wasSet = true;
		return wasSet;
	}

	public boolean setEmail(String aEmail) {
		boolean wasSet = false;
		email = aEmail;
		wasSet = true;
		return wasSet;
	}

	public boolean setPhone(String aPhone) {
		boolean wasSet = false;
		phone = aPhone;
		wasSet = true;
		return wasSet;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	/* Code from template association_GetOne */
	public SCRS getScrs() {
		return scrs;
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
			existingScrs.removeUser(this);
		}
		scrs.addUser(this);
		wasSet = true;
		return wasSet;
	}

	public void delete() {
		SCRS placeholderScrs = scrs;
		this.scrs = null;
		if (placeholderScrs != null) {
			placeholderScrs.removeUser(this);
		}
	}

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "," + "password" + ":" + getPassword() + ","
				+ "email" + ":" + getEmail() + "," + "phone" + ":" + getPhone() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "scrs = "
				+ (getScrs() != null ? Integer.toHexString(System.identityHashCode(getScrs())) : "null");
	}
}
