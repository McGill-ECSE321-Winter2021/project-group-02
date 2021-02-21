package ca.mcgill.ecse321.scrs.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorColumn(name="TYPE")
public abstract class User {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// User Attributes
	private String name;
	private String password;
	private String email;
	private String phone;

	// User Associations
	private SCRS scrs;
	private int userID;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public User(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, int id) {
		name = aName;
		password = aPassword;
		email = aEmail;
		phone = aPhone;
		userID= id;
		boolean didAddScrs = setScrs(aScrs);
		if (!didAddScrs) {
			throw new RuntimeException(
					"Unable to create user due to scrs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	protected User() {}

	// ------------------------
	// INTERFACE
	// ------------------------

	@Id
	public int getId() {
		return this.userID;
	}

	public void setId(int id) {
		this.userID = id;
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
	@ManyToOne
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
