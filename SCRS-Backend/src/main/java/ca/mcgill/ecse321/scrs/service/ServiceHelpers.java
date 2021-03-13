package ca.mcgill.ecse321.scrs.service;

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
}
