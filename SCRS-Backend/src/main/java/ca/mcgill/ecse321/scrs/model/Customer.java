package ca.mcgill.ecse321.scrs.model;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer extends User {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Customer Attributes
	private String customerID;

	// Customer Associations
	private List<Appointment> appointments;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Customer(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, String aCustomerID) {
		super(aName, aPassword, aEmail, aPhone, aScrs);
		customerID = aCustomerID;
		appointments = new ArrayList<Appointment>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setCustomerID(String aCustomerID) {
		boolean wasSet = false;
		customerID = aCustomerID;
		wasSet = true;
		return wasSet;
	}

	@Id
	public String getCustomerID() {
		return customerID;
	}

	/* Code from template association_GetMany */
	public Appointment getAppointment(int index) {
		Appointment aAppointment = appointments.get(index);
		return aAppointment;
	}

	public List<Appointment> getAppointments() {
		List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
		return newAppointments;
	}

	public int numberOfAppointments() {
		int number = appointments.size();
		return number;
	}

	public boolean hasAppointments() {
		boolean has = appointments.size() > 0;
		return has;
	}

	public int indexOfAppointment(Appointment aAppointment) {
		int index = appointments.indexOf(aAppointment);
		return index;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfAppointments() {
		return 0;
	}

	/* Code from template association_AddManyToOne */
	public Appointment addAppointment(String aAppointmentID,
			ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType aAppointmentType, String aService, String aNote,
			int aRating, String aFeedback, boolean aPaid, SCRS aScrs, Timeslot... allTimeslots) {
		return new Appointment(aAppointmentID, aAppointmentType, aService, aNote, aRating, aFeedback, aPaid, this,
				aScrs, allTimeslots);
	}

	public boolean addAppointment(Appointment aAppointment) {
		boolean wasAdded = false;
		if (appointments.contains(aAppointment)) {
			return false;
		}
		Customer existingCustomer = aAppointment.getCustomer();
		boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
		if (isNewCustomer) {
			aAppointment.setCustomer(this);
		} else {
			appointments.add(aAppointment);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeAppointment(Appointment aAppointment) {
		boolean wasRemoved = false;
		// Unable to remove aAppointment, as it must always have a customer
		if (!this.equals(aAppointment.getCustomer())) {
			appointments.remove(aAppointment);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addAppointmentAt(Appointment aAppointment, int index) {
		boolean wasAdded = false;
		if (addAppointment(aAppointment)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfAppointments()) {
				index = numberOfAppointments() - 1;
			}
			appointments.remove(aAppointment);
			appointments.add(index, aAppointment);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index) {
		boolean wasAdded = false;
		if (appointments.contains(aAppointment)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfAppointments()) {
				index = numberOfAppointments() - 1;
			}
			appointments.remove(aAppointment);
			appointments.add(index, aAppointment);
			wasAdded = true;
		} else {
			wasAdded = addAppointmentAt(aAppointment, index);
		}
		return wasAdded;
	}

	public void delete() {
		for (int i = appointments.size(); i > 0; i--) {
			Appointment aAppointment = appointments.get(i - 1);
			aAppointment.delete();
		}
		super.delete();
	}

	public String toString() {
		return super.toString() + "[" + "customerID" + ":" + getCustomerID() + "]";
	}
}