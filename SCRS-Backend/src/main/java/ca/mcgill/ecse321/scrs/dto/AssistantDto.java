package ca.mcgill.ecse321.scrs.dto;

public class AssistantDto
{
    private int scrsUserId;
    private String name;
    private String email;
    private String phone;

    public AssistantDto()
    {
    }

    public AssistantDto(int id, String name)
    {
        this(id, name, null, null);
    }

    public AssistantDto(int id, String name, String email, String phone)
    {
        scrsUserId = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getAssistantId()
    {
        return scrsUserId;
    }

    public String getAssistantName()
    {
        return name;
    }

    public String getAssistantEmail()
    {
        return email;
    }

    public String getAssistantPhone()
    {
        return phone;
    }

    public void setAssistantEmail(String email)
    {
        this.email = email;
    }

    public void setAssistantPhone(String phone)
    {
        this.phone = phone;
    }
}
