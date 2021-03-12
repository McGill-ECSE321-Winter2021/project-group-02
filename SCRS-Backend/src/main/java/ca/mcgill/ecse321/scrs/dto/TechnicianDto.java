package ca.mcgill.ecse321.scrs.dto;

import java.util.Collections;
import java.util.List;

public class TechnicianDto
{
    private int scrsUserId;
    private String name;
    private String email;
    private String phone;
    private List<TimeslotDto> availabilities;

    public TechnicianDto()
    {
    }

    @SuppressWarnings("unchecked")
    public TechnicianDto(int id, String name)
    {
        this(id, name, null, null, Collections.EMPTY_LIST);
    }

    @SuppressWarnings("unchecked")
    public TechnicianDto(int id, String name, String email, String phone) {
        this(id, name, email, phone, Collections.EMPTY_LIST);
    }

    public TechnicianDto(int id, String name, List<TimeslotDto> availabilityList)
    {
        this(id, name, null, null, availabilityList);
    }

    public TechnicianDto(int id, String name, String email, String phone, List<TimeslotDto> availabilityList)
    {
        scrsUserId = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        availabilities = availabilityList;
    }

    public int getTechnicianId()
    {
        return scrsUserId;
    }

    public String getTechnicianName()
    {
        return name;
    }

    public String getTechnicianEmail()
    {
        return email;
    }

    public String getTechnicianPhone()
    {
        return phone;
    }

    public List<TimeslotDto> getTechnicianAvailabilities() {
        return availabilities;
    }

    public void setTechnicianEmail(String email)
    {
        this.email = email;
    }

    public void setTechnicianPhone(String phone)
    {
        this.phone = phone;
    }

    public void setTechnicianAvailabilities(List<TimeslotDto> availabilityList) {
        availabilities = availabilityList;
    }
}
