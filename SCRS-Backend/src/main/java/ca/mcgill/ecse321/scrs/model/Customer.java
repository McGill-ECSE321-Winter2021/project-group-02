package ca.mcgill.ecse321.scrs.model;

import javax.persistence.Entity;

@Entity
public class Customer extends SCRSUser
{
    // Customer Associations

    public Customer(String aName, String aPassword, String aEmail, String aPhone, SCRS aScrs)
    {
        super(aName, aPassword, aEmail, aPhone, aScrs);
    }

    public Customer()
    {
        super();
    }

    public void delete()
    {
        super.delete();
    }

    public String toString()
    {
        return super.toString();
    }
}