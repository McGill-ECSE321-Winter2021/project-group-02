package ca.mcgill.ecse321.scrs.dto;

import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDto
{
    private int appointmentId;
    private AppointmentType appointmentType;
    private String service;
    private String note;
    private int rating;
    private String feedback;
    private boolean paymentStatus;
    private CustomerDto customer;
    private List<TimeslotDto> timeslots;

    public AppointmentDto()
    {
    }

    public AppointmentDto(int id, AppointmentType type, String service, String note, CustomerDto customer, List<TimeslotDto> timeslotList)
    {
        this(id, type, service, note, -1, null, false, customer, timeslotList);
    }

    @SuppressWarnings("unchecked")
    public AppointmentDto(int id, AppointmentType type, String service, String note, CustomerDto customer)
    {
        this(id, type, service, note, -1, null, false, customer, new ArrayList<TimeslotDto>());
    }

    public AppointmentDto(int id, AppointmentType type, String service, String note, int rating, String feedback, boolean isPaid, CustomerDto customer, List<TimeslotDto> timeslotList)
    {
        appointmentId = id;
        appointmentType = type;
        this.service = service;
        this.note = note;
        this.rating = rating;
        this.feedback = feedback;
        paymentStatus = isPaid;
        this.customer = customer;
        timeslots = timeslotList;
    }

    public int getAppointmentId()
    {
        return appointmentId;
    }

    public AppointmentType getAppointmentType()
    {
        return appointmentType;
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

    public CustomerDto getCustomer()
    {
        return customer;
    }

    public List<TimeslotDto> getTimeslots()
    {
        return timeslots;
    }

    public void setTimeslots(List<TimeslotDto> timeslots)
    {
        this.timeslots = timeslots;
    }
}
