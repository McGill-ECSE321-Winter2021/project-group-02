package ca.mcgill.ecse321.scrs.dto;

public class CustomerDto
{
    private int scrsUserId;
    private String name;
    private String email;
    private String phone;

    public CustomerDto()
    {
    }

    public CustomerDto(int id, String name)
    {
        this(id, name, null, null);
    }

    public CustomerDto(int id, String name, String email, String phone)
    {
        scrsUserId = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public void setCustomerEmail(String email)
    {
        this.email = email;
    }

    public String getCustomerPhone()
    {
        return phone;
    }

    public void setCustomerPhone(String phone)
    {
        this.phone = phone;
    }
}
