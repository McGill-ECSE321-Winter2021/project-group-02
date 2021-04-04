package ca.mcgill.ecse321.scrs.model;

import javax.persistence.Entity;

@Entity
public class Technician extends SCRSUser
{
    public Technician(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs)
    {
        super(aName, aPassword, aEmail, aPhone, aScrs);
    }

    protected Technician()
    {
        super();
    }

    public String toString()
    {
        return super.toString();
    }
}