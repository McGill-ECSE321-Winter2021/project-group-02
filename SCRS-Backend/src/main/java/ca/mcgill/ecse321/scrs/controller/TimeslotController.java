package ca.mcgill.ecse321.scrs.controller;


import ca.mcgill.ecse321.scrs.dto.TimeslotDto;
import ca.mcgill.ecse321.scrs.dto.WorkspaceDto;
import ca.mcgill.ecse321.scrs.model.Appointment.AppointmentType;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import ca.mcgill.ecse321.scrs.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/timeslot")
public class TimeslotController
{

    @Autowired
    TimeslotService timeslotService;

    @GetMapping(value = {"/getServiceTimeslot", "/getServiceTimeslot/"})
    public List<TimeslotDto> getServiceTimeslot(@RequestParam(name = "appointmentType") AppointmentType type)
    {
        // TODO find available timeslots based on selected appointment type
        return null;
    }

    // ========== Helper Methods ==========

    public static TimeslotDto convertToDto(Timeslot timeslot)
    {
        if(timeslot == null)
            throw  new IllegalArgumentException("There is no such timeslot!");
        WorkspaceDto workspaceDto = WorkspaceController.convertToDto(timeslot.getWorkspace());
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
}
