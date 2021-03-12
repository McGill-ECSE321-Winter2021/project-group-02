package ca.mcgill.ecse321.scrs.dto;

import java.util.Collections;
import java.util.List;

public class CustomerDto
{
    private int scrsUserId;
    private String name;
    private String email;
    private String phone;
    private List<AppointmentDto> appointments;

    public CustomerDto()
    {
    }

    @SuppressWarnings("unchecked")
    public CustomerDto(int id, String name)
    {
        this(id, name, null, null, Collections.EMPTY_LIST);
    }

    @SuppressWarnings("unchecked")
    public CustomerDto(int id, String name, String email, String phone) {
        this(id, name, email, phone, Collections.EMPTY_LIST);
    }

    public CustomerDto(int id, String name, List<AppointmentDto> appointmentList)
    {
        this(id, name, null, null, appointmentList);
    }

    public CustomerDto(int id, String name, String email, String phone, List<AppointmentDto> appointmentList)
    {
        scrsUserId = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        appointments = appointmentList;
    }

    public int getCustomerId()
    {
        return scrsUserId;
    }

    public String getCustomerName()
    {
        return name;
    }

    public String getCustomerEmail()
    {
        return email;
    }

    public String getCustomerPhone()
    {
        return phone;
    }

    public List<AppointmentDto> getCustomerAppointments() {
        return appointments;
    }

    public void setCustomerEmail(String email)
    {
        this.email = email;
    }

    public void setCustomerPhone(String phone)
    {
        this.phone = phone;
    }

    public void setCustomerAppointments(List<AppointmentDto> appointmentList) {
        appointments = appointmentList;
    }
}
