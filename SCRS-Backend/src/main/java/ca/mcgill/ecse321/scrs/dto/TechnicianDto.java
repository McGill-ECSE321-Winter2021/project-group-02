package ca.mcgill.ecse321.scrs.dto;

public class TechnicianDto
{
    private int scrsUserId;
    private String name;
    private String email;
    private String phone;
    public TechnicianDto()
    {
    }

    @SuppressWarnings("unchecked")
    public TechnicianDto(int id, String name)
    {
        this(id, name, null, null);
    }

    public TechnicianDto(int id, String name, String email, String phone)
    {
        scrsUserId = id;
        this.name = name;
        this.email = email;
        this.phone = phone;

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

    public void setTechnicianEmail(String email)
    {
        this.email = email;
    }

    public void setTechnicianPhone(String phone)
    {
        this.phone = phone;
    }
}
