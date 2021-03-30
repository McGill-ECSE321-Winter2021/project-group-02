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

    public static String hash(String string)
    {
        if (string != null)
        {
            try
            {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = digest.digest(
                        string.getBytes(StandardCharsets.UTF_8));
                return bytesToHex(encodedHash);
            } catch (NoSuchAlgorithmException e)
            {
                System.out.println("bad algorithm");
            }
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

    public static boolean isAdmin(SCRSUser user)
    {
        if (user == null) return false;
        else return user instanceof Assistant;
    }

    // ========== DTO Conversion ==========

    public static AppointmentDto convertToDto(Appointment appointment)
    {
        if (appointment == null)
        {
            throw new IllegalArgumentException("There is no such appointment!");
        }
        if (appointment.getCustomer() == null)
        {
            throw new IllegalArgumentException("Appointment is ill-formed. Does not associate with valid customer!");
        }
        if (appointment.getAppointmentType() == null)
        {
            throw new IllegalArgumentException("Appointment is ill-formed. Invalid appointmentType");
        }
        AppointmentDto appointmentDto = new AppointmentDto(appointment.getAppointmentID(), appointment.getAppointmentType().toString(), appointment.getService(), appointment.getNote(), appointment.getRating(), appointment.getFeedback(), appointment.getPaid(), appointment.getCustomer().getScrsUserId());
        appointmentDto.setTimeslots(appointment.getTimeslots());
        return appointmentDto;
    }

    public static AssistantDto convertToDto(Assistant assistant)
    {
        if (assistant == null)
        {
            throw new IllegalArgumentException("There is no such assistant!");
        }
        return new AssistantDto(assistant.getScrsUserId(), assistant.getName(), assistant.getEmail(), assistant.getPhone());
    }

    public static CustomerDto convertToDto(Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("There is no such customer!");
        }
        return new CustomerDto(customer.getScrsUserId(), customer.getName(), customer.getEmail(), customer.getPhone());
    }

    public static TechnicianDto convertToDto(Technician technician)
    {
        if (technician == null)
        {
            throw new IllegalArgumentException("There is no such technician!");
        }
        return new TechnicianDto(technician.getScrsUserId(), technician.getName(), technician.getEmail(), technician.getPhone());
    }

    public static List<TechnicianDto> convertToDtoList(List<Technician> technicians)
    {
        if (technicians == null)
        {
            throw new IllegalArgumentException("There is no such timeslot list!");
        }
        ArrayList<TechnicianDto> technicianDtos = new ArrayList<>();
        for (Technician t: technicians)
        {
            technicianDtos.add(convertToDto(t));
        }
        return technicianDtos;
    }

    public static TimeslotDto convertToDto(Timeslot timeslot)
    {
        if (timeslot == null)
        {
            throw new IllegalArgumentException("There is no such timeslot!");
        }
        TimeslotDto timeslotDto = new TimeslotDto(timeslot.getTimeSlotID(), timeslot.getStartDate(), timeslot.getEndDate(), timeslot.getStartTime(), timeslot.getEndTime(), timeslot.getWorkspace().getWorkspaceID());
        timeslotDto.setTechnicians(timeslot.getTechnicians());
        return timeslotDto;
    }

    public static List<TimeslotDto> convertToDto(List<Timeslot> timeslots)
    {
        if (timeslots == null)
        {
            throw new IllegalArgumentException("There is no such timeslot list!");
        }
        ArrayList<TimeslotDto> timeslotsDto = new ArrayList<>();
        for (Timeslot timeslot: timeslots)
        {
            timeslotsDto.add(convertToDto(timeslot));
        }
        return timeslotsDto;
    }

    public static WorkspaceDto convertToDto(Workspace workspace)
    {
        if (workspace == null)
        {
            throw new IllegalArgumentException("There is no such workspace!");
        }
        WorkspaceDto workspaceDto = new WorkspaceDto(workspace.getWorkspaceID(), workspace.getSpaceName());
        workspaceDto.setTimeslotsId(workspace.getAvailabilities());
        return workspaceDto;
    }
}
