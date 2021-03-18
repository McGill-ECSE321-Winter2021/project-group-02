package ca.mcgill.ecse321.scrs.dto;

import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;
import ca.mcgill.ecse321.scrs.model.Timeslot;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDto
{
    private int appointmentId;
    private String appointmentType;
    private String service;
    private String note;
    private int rating;
    private String feedback;
    private boolean paymentStatus;
    private int customerId;
    private ArrayList<Integer> timeslotsId;

    public AppointmentDto()
    {
    }

    public AppointmentDto(int id, String type, String service, String note, int customerId, ArrayList<Integer> timeslotsId)
    {
        this(id, type, service, note, -1, null, false, customerId, timeslotsId);
    }

    public AppointmentDto(int id, String type, String service, String note, int rating, String feedback, boolean isPaid, int customerId)
    {
        this(id, type, service, note, rating, feedback, isPaid, customerId, new ArrayList<Integer>());
    }

    public AppointmentDto(int id, String type, String service, String note, int rating, String feedback, boolean isPaid, int customerId, ArrayList<Integer> timeslotsId)
    {
        appointmentId = id;
        appointmentType = type;
        this.service = service;
        this.note = note;
        this.rating = rating;
        this.feedback = feedback;
        paymentStatus = isPaid;
        this.customerId = customerId;
        this.timeslotsId = timeslotsId;
    }

    public int getAppointmentId()
    {
        return appointmentId;
    }

    public AppointmentType getAppointmentType()
    {
        return AppointmentType.valueOf(appointmentType);
    }

    public String getService()
    {
        return service;
    }

    public String getNote()
    {
        return note;
    }

    public int getRating()
    {
        return rating;
    }

    public String getFeedback()
    {
        return feedback;
    }

    public boolean getPaymentStatus()
    {
        return paymentStatus;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public ArrayList<Integer> getTimeslotsId()
    {
        return timeslotsId;
    }

    public void setTimeslots(List<Timeslot> timeslots)
    {
        this.timeslotsId = new ArrayList<Integer>();
        for (Timeslot timeslot: timeslots)
        {
            timeslotsId.add(timeslot.getTimeSlotID());
        }
    }
}
