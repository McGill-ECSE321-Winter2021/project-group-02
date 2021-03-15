package ca.mcgill.ecse321.scrs.controller;

import ca.mcgill.ecse321.scrs.dto.*;
import ca.mcgill.ecse321.scrs.model.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Helper
{
    //helper functions

    public static String hash(String string){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    string.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        }catch (NoSuchAlgorithmException e){
            System.out.println("bad algorithm");
        }
        return "error";
    }

    // from https://www.baeldung.com/sha-256-hashing-java
    private static String bytesToHex(byte[] hash)
    {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
            {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // ============== DTO CONVERSIONS ===========

    public static AppointmentDto convertToDto(Appointment a)
    {
        if (a == null)
            throw new IllegalArgumentException("There is no such appointment!");
        CustomerDto customerDto = convertToDTO(a.getCustomer());
        List<TimeslotDto> timeslots = convertToDto(a.getTimeslots());
        return new AppointmentDto(a.getAppointmentID(), a.getAppointmentType(), a.getService(), a.getNote(), customerDto, timeslots);
    }

    public static AssistantDto convertToDTO(Assistant a)
    {
        if (a == null) throw new IllegalArgumentException("There is no such assistant!");
        return new AssistantDto(a.getScrsUserId(), a.getName(), a.getEmail(), a.getPhone());
    }

    public static CustomerDto convertToDTO(Customer c)
    {
        if (c == null) throw new IllegalArgumentException("There is no such customer!");
        return new CustomerDto(c.getScrsUserId(), c.getName(), c.getEmail(), c.getPhone());
    }

    public static TechnicianDto convertToDTO(Technician t)
    {
        if (t == null) throw new IllegalArgumentException("There is no such assistant!");
        return new TechnicianDto(t.getScrsUserId(), t.getName(), t.getEmail(), t.getPhone());
    }

    public static TimeslotDto convertToDto(Timeslot timeslot)
    {
        if(timeslot == null)
            throw  new IllegalArgumentException("There is no such timeslot!");
        WorkspaceDto workspaceDto = convertToDto(timeslot.getWorkspace());
        // check technician convertor
        return new TimeslotDto(timeslot.getTimeSlotID(), timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getStartTime(), timeslot.getEndTime(), workspaceDto);
    }

    public static List<TimeslotDto> convertToDto(List<Timeslot> timeslots)
    {
        List<TimeslotDto> timeslotDtos = new ArrayList<>();
        for (Timeslot timeslot : timeslots) {
            timeslotDtos.add(convertToDto(timeslot));
        }
        return timeslotDtos;
    }

    public static WorkspaceDto convertToDto(Workspace workspace)
    {
        if (workspace == null)
            throw new IllegalArgumentException("There is no such workspace!");
        return new WorkspaceDto(workspace.getWorkspaceID(), workspace.getSpaceType());
    }
}
