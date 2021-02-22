package ca.mcgill.ecse321.scrs.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "TYPE")
public abstract class SCRSUser
{
    // Account Attributes
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int scrsUserId;
    private String name;
    private String password;
    private String email;
    private String phone;

    // Account Associations
    @ManyToOne
    private SCRS scrs;

    public SCRSUser(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs, int aScrsUserId)
    {
        name = aName;
        password = aPassword;
        email = aEmail;
        phone = aPhone;
        scrsUserId = aScrsUserId;
        boolean didAddScrs = setScrs(aScrs);
        if (!didAddScrs)
        {
            throw new RuntimeException(
                    "Unable to create user due to scrs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    public SCRSUser(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs)
    {
        name = aName;
        password = aPassword;
        email = aEmail;
        phone = aPhone;
        boolean didAddScrs = setScrs(aScrs);
        if (!didAddScrs)
        {
            throw new RuntimeException(
                    "Unable to create user due to scrs. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    protected SCRSUser()
    {
        name = "Invalid";
        password = "Invalid";
        email = "Invalid";
        phone = "Invalid";
        scrs = null;
    };

    public int getScrsUserId()
    {
        return this.scrsUserId;
    }

    public void setScrsUserId(int UserId)
    {
        this.scrsUserId = UserId;
    }

    public boolean setName(String aName)
    {
        name = aName;
        return true;
    }

    public boolean setPassword(String aPassword)
    {
        password = aPassword;
        return true;
    }

    public boolean setEmail(String aEmail)
    {
        email = aEmail;
        return true;
    }

    public boolean setPhone(String aPhone)
    {
        phone = aPhone;
        return true;
    }

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone()
    {
        return phone;
    }

    public SCRS getScrs()
    {
        return scrs;
    }

    public boolean setScrs(SCRS aScrs)
    {
        boolean wasSet = false;
        if (aScrs == null)
        {
            return wasSet;
        }

        SCRS existingScrs = scrs;
        scrs = aScrs;
        if (existingScrs != null && !existingScrs.equals(aScrs))
        {
            existingScrs.removeUser(this);
        }
        scrs.addUser(this);
        wasSet = true;
        return wasSet;
    }

    public void delete()
    {
        SCRS placeholderScrs = scrs;
        this.scrs = null;
        if (placeholderScrs != null)
        {
            placeholderScrs.removeUser(this);
        }
    }

    public String toString()
    {
        return super.toString() + "[" + "name" + ":" + getName() + "," + "password" + ":" + getPassword() + ","
                + "email" + ":" + getEmail() + "," + "phone" + ":" + getPhone() + "]"
                + System.getProperties().getProperty("line.separator") + "  " + "scrs = "
                + (getScrs() != null ? Integer.toHexString(System.identityHashCode(getScrs())) : "null");
    }
}
