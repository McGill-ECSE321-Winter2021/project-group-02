package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.model.SCRSUser;

import java.util.ArrayList;
import java.util.List;


public class ServiceHelpers
{
    public static <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    public static void checkAccountInfoValidity(String email, String name, String password, String phone)
    {
        if(email == null || email.trim().length() == 0) throw new IllegalArgumentException("Please submit a valid email.");
        if(name == null || name.trim().length() == 0) throw new IllegalArgumentException("Please submit a valid name.");
        if(password == null || password.trim().length() == 0) throw new IllegalArgumentException("Please submit a valid password.");
        if(phone == null || phone.trim().length() == 0) throw new IllegalArgumentException("Please submit a valid phone.");
    }

    public static void checkAccountInfoValidity(SCRSUser user)
    {
        if (user == null) throw new IllegalArgumentException("Please submit a valid user object.");
        if(user.getEmail() == null || user.getEmail().trim().length() == 0) throw new IllegalArgumentException("Please submit a valid email.");
        if(user.getName() == null || user.getName().trim().length() == 0) throw new IllegalArgumentException("Please submit a valid name.");
        if(user.getPassword() == null || user.getPassword().trim().length() == 0) throw new IllegalArgumentException("Please submit a valid password.");
        if(user.getPhone() == null || user.getPhone().trim().length() == 0) throw new IllegalArgumentException("Please submit a valid phone.");
    }
}
