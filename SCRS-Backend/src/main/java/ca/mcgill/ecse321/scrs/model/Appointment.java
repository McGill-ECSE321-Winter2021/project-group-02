package ca.mcgill.ecse321.scrs.model;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Appointment {

	// ------------------------
	// ENUMERATIONS
	// ------------------------

	public enum AppointmentType {
		CarWash, Maintenance, OilChange, TireChange, Towing, Inspection, RoadsideAssistance, Checkup, Other
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Appointment Attributes
	private int appointmentID;
	private AppointmentType appointmentType;
	private String service;
	private String note;
	private int rating;
	private String feedback;
	private boolean paid;

	// Appointment Associations
	private Customer customer;
	private List<Timeslot> timeslots;
	private SCRS scrs;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Appointment(int aAppointmentID, AppointmentType aAppointmentType, String aService, String aNote,
			int aRating, String aFeedback, boolean aPaid, Customer aCustomer, SCRS aScrs, Timeslot... allTimeslots) {
		appointmentID = aAppointmentID;
		appointmentType = aAppointmentType;
		service = aService;
		note = aNote;
		rating = aRating;
		feedback = aFeedback;
		paid = aPaid;
		boolean didAddCustomer = setCustomer(aCustomer);
		if (!didAddCustomer) {
			throw new RuntimeException(
					"Unable to create appointment due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		timeslots = new ArrayList<Timeslot>();
		boolean didAddTimeslots = setTimeslots(allTimeslots);
		if (!didAddTimeslots) {
			throw new RuntimeException(
					"Unable to create Appointment, must have at least 1 timeslots. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		boolean didAddScrs = setScrs(aScrs);
		if (!didAddScrs) {
			throw new RuntimeException(
					"Unable to create appointment due to scrs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	protected Appointment() {}

	public boolean setAppointmentID(int aAppointmentID) {
		boolean wasSet = false;
		appointmentID = aAppointmentID;
		wasSet = true;
		return wasSet;
	}

	public boolean setAppointmentType(AppointmentType aAppointmentType) {
		boolean wasSet = false;
		appointmentType = aAppointmentType;
		wasSet = true;
		return wasSet;
	}

	public boolean setService(String aService) {
		boolean wasSet = false;
		service = aService;
		wasSet = true;
		return wasSet;
	}

	public boolean setNote(String aNote) {
		boolean wasSet = false;
		note = aNote;
		wasSet = true;
		return wasSet;
	}

	public boolean setRating(int aRating) {
		boolean wasSet = false;
		rating = aRating;
		wasSet = true;
		return wasSet;
	}

	public boolean setFeedback(String aFeedback) {
		boolean wasSet = false;
		feedback = aFeedback;
		wasSet = true;
		return wasSet;
	}

	public boolean setPaid(boolean aPaid) {
		boolean wasSet = false;
		paid = aPaid;
		wasSet = true;
		return wasSet;
	}
	
	@Id
	public int getAppointmentID() {
		return appointmentID;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public String getService() {
		return service;
	}

	public String getNote() {
		return note;
	}

	public int getRating() {
		return rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public boolean getPaid() {
		return paid;
	}

	@ManyToOne
	public Customer getCustomer() {
		return customer;
	}

	/* Code from template association_GetMany */
	public Timeslot getTimeslot(int index) {
		Timeslot aTimeslot = timeslots.get(index);
		return aTimeslot;
	}
	
	@OneToMany //0..1 to 1..*
	public List<Timeslot> getTimeslots() {
		List<Timeslot> newTimeslots = Collections.unmodifiableList(timeslots);
		return newTimeslots;
	}

	public int numberOfTimeslots() {
		int number = timeslots.size();
		return number;
	}

	public boolean hasTimeslots() {
		boolean has = timeslots.size() > 0;
		return has;
	}

	public int indexOfTimeslot(Timeslot aTimeslot) {
		int index = timeslots.indexOf(aTimeslot);
		return index;
	}

	/* Code from template association_GetOne */
	@ManyToOne
	public SCRS getScrs() {
		return scrs;
	}

	/* Code from template association_SetOneToMany */
	public boolean setCustomer(Customer aCustomer) {
		boolean wasSet = false;
		if (aCustomer == null) {
			return wasSet;
		}

		Customer existingCustomer = customer;
		customer = aCustomer;
		if (existingCustomer != null && !existingCustomer.equals(aCustomer)) {
			existingCustomer.removeAppointment(this);
		}
		customer.addAppointment(this);
		wasSet = true;
		return wasSet;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfTimeslots() {
		return 1;
	}

	/* Code from template association_AddMNToOptionalOne */
	public boolean addTimeslot(Timeslot aTimeslot) {
		boolean wasAdded = false;
		if (timeslots.contains(aTimeslot)) {
			return false;
		}
		Appointment existingAppointment = aTimeslot.getAppointment();
		if (existingAppointment != null && existingAppointment.numberOfTimeslots() <= minimumNumberOfTimeslots()) {
			return wasAdded;
		} else if (existingAppointment != null) {
			existingAppointment.timeslots.remove(aTimeslot);
		}
		timeslots.add(aTimeslot);
		setAppointment(aTimeslot, this);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeTimeslot(Timeslot aTimeslot) {
		boolean wasRemoved = false;
		if (timeslots.contains(aTimeslot) && numberOfTimeslots() > minimumNumberOfTimeslots()) {
			timeslots.remove(aTimeslot);
			setAppointment(aTimeslot, null);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_SetMNToOptionalOne */
	public boolean setTimeslots(Timeslot... newTimeslots) {
		boolean wasSet = false;
		if (newTimeslots.length < minimumNumberOfTimeslots()) {
			return wasSet;
		}

		ArrayList<Timeslot> checkNewTimeslots = new ArrayList<Timeslot>();
		HashMap<Appointment, Integer> appointmentToNewTimeslots = new HashMap<Appointment, Integer>();
		for (Timeslot aTimeslot : newTimeslots) {
			if (checkNewTimeslots.contains(aTimeslot)) {
				return wasSet;
			} else if (aTimeslot.getAppointment() != null && !this.equals(aTimeslot.getAppointment())) {
				Appointment existingAppointment = aTimeslot.getAppointment();
				if (!appointmentToNewTimeslots.containsKey(existingAppointment)) {
					appointmentToNewTimeslots.put(existingAppointment,
							new Integer(existingAppointment.numberOfTimeslots()));
				}
				Integer currentCount = appointmentToNewTimeslots.get(existingAppointment);
				int nextCount = currentCount - 1;
				if (nextCount < 1) {
					return wasSet;
				}
				appointmentToNewTimeslots.put(existingAppointment, new Integer(nextCount));
			}
			checkNewTimeslots.add(aTimeslot);
		}

		timeslots.removeAll(checkNewTimeslots);

		for (Timeslot orphan : timeslots) {
			setAppointment(orphan, null);
		}
		timeslots.clear();
		for (Timeslot aTimeslot : newTimeslots) {
			if (aTimeslot.getAppointment() != null) {
				aTimeslot.getAppointment().timeslots.remove(aTimeslot);
			}
			setAppointment(aTimeslot, this);
			timeslots.add(aTimeslot);
		}
		wasSet = true;
		return wasSet;
	}

	/* Code from template association_GetPrivate */
	private void setAppointment(Timeslot aTimeslot, Appointment aAppointment) {
		try {
			java.lang.reflect.Field mentorField = aTimeslot.getClass().getDeclaredField("appointment");
			mentorField.setAccessible(true);
			mentorField.set(aTimeslot, aAppointment);
		} catch (Exception e) {
			throw new RuntimeException("Issue internally setting aAppointment to aTimeslot", e);
		}
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addTimeslotAt(Timeslot aTimeslot, int index) {
		boolean wasAdded = false;
		if (addTimeslot(aTimeslot)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfTimeslots()) {
				index = numberOfTimeslots() - 1;
			}
			timeslots.remove(aTimeslot);
			timeslots.add(index, aTimeslot);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveTimeslotAt(Timeslot aTimeslot, int index) {
		boolean wasAdded = false;
		if (timeslots.contains(aTimeslot)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfTimeslots()) {
				index = numberOfTimeslots() - 1;
			}
			timeslots.remove(aTimeslot);
			timeslots.add(index, aTimeslot);
			wasAdded = true;
		} else {
			wasAdded = addTimeslotAt(aTimeslot, index);
		}
		return wasAdded;
	}

	/* Code from template association_SetOneToMany */
	@ManyToOne
	public boolean setScrs(SCRS aScrs) {
		boolean wasSet = false;
		if (aScrs == null) {
			return wasSet;
		}

		SCRS existingScrs = scrs;
		scrs = aScrs;
		if (existingScrs != null && !existingScrs.equals(aScrs)) {
			existingScrs.removeAppointment(this);
		}
		scrs.addAppointment(this);
		wasSet = true;
		return wasSet;
	}

	public void delete() {
		Customer placeholderCustomer = customer;
		this.customer = null;
		if (placeholderCustomer != null) {
			placeholderCustomer.removeAppointment(this);
		}
		for (Timeslot aTimeslot : timeslots) {
			setAppointment(aTimeslot, null);
		}
		timeslots.clear();
		SCRS placeholderScrs = scrs;
		this.scrs = null;
		if (placeholderScrs != null) {
			placeholderScrs.removeAppointment(this);
		}
	}

	public String toString() {
		return super.toString() + "[" + "appointmentID" + ":" + getAppointmentID() + "," + "service" + ":"
				+ getService() + "," + "note" + ":" + getNote() + "," + "rating" + ":" + getRating() + "," + "feedback"
				+ ":" + getFeedback() + "," + "paid" + ":" + getPaid() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "appointmentType" + "="
				+ (getAppointmentType() != null
						? !getAppointmentType().equals(this) ? getAppointmentType().toString().replaceAll("  ", "    ")
								: "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "customer = "
				+ (getCustomer() != null ? Integer.toHexString(System.identityHashCode(getCustomer())) : "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "scrs = "
				+ (getScrs() != null ? Integer.toHexString(System.identityHashCode(getScrs())) : "null");
	}
}