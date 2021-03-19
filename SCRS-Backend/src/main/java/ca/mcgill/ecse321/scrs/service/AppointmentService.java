package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AppointmentRepository;
import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
import ca.mcgill.ecse321.scrs.dto.AppointmentDto;
import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TimeslotRepository timeslotRepository;

    @Transactional
    public Appointment createAppointment(Appointment.AppointmentType appointmentType, String service, String note, boolean paid, Customer customer, Timeslot... timeslots) {
        if(appointmentType == null) throw new IllegalArgumentException("Please submit a valid appointment type.");
        if(customer == null || customerRepository.findByScrsUserId(customer.getScrsUserId()) == null) throw new IllegalArgumentException("Please submit a valid customer.");
        try
        {
            if(timeslots.length == 0 || timeslotRepository.findByTimeSlotID(timeslots[0].getTimeSlotID()) == null) throw new IllegalArgumentException("Please select at least one valid timeslot.");
        } catch (NullPointerException e)
        {
            throw new IllegalArgumentException("Please select at least one valid timeslot.");
        }
        Appointment appointment = new Appointment();
        appointment.setAppointmentType(appointmentType);
        appointment.setService(service);
        appointment.setCustomer(customer);
        appointment.setTimeslots(timeslots);
        appointment.setNote(note);
        appointment.setPaid(paid);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Transactional
    public List<Appointment> getAllAppointments() {
        return toList(appointmentRepository.findAll());
    }

    @Transactional
    public Appointment getAppointmentById(int id) {
        return appointmentRepository.findByAppointmentID(id);
    }

    @Transactional
    public List<Appointment> getAppointmentsByCustomer(Customer customer) {
        return appointmentRepository.findAppointmentsByCustomer(customer);
    }

    @Transactional
    public Appointment getAppointmentByTimeslot(Timeslot timeslot) {
        return appointmentRepository.findByTimeslotsContains(timeslot);
    }

    @Transactional
    public Appointment rateAppointment(int appointmentId, int rating) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment == null) throw new IllegalArgumentException("No such appointment!");
        if (rating > 10 || rating < 0) throw new IllegalArgumentException("Invalid rating");
        appointment.setRating(rating);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Transactional
    public Appointment modifyAppointment(AppointmentDto appt)
    {
        if (appt == null) throw new IllegalArgumentException("Invalid appointment");
        if (appt == null) throw new IllegalArgumentException("Invalid appointment");
        if (appt.getAppointmentType() == null) throw new IllegalArgumentException("Invalid appointment type.");
        if (appt.getCustomerId() == -1) throw new IllegalArgumentException("Invalid customer.");
        if (appt.getTimeslotsId() == null || appt.getTimeslotsId().size() == 0) throw new IllegalArgumentException("No valid timeslots selected.");
        if (appt.getRating() > 10 || appt.getRating() < 0) throw new IllegalArgumentException("Invalid rating");
  
        Appointment apptToModify = appointmentRepository.findByAppointmentID(appt.getAppointmentId());
        if (apptToModify == null) throw new IllegalArgumentException("No such appointment exists");

        apptToModify.setAppointmentType(appt.getAppointmentType());
        apptToModify.setService(appt.getService());
        apptToModify.setNote(appt.getNote());
        apptToModify.setRating(appt.getRating());
        apptToModify.setFeedback(appt.getFeedback());
        apptToModify.setPaid(appt.getPaymentStatus());

        List<Integer> timeslots = appt.getTimeslotsId();
        apptToModify.setTimeslots(); // clear timeslots
        if (timeslots != null)
        {
            // if we are passed some timeslot ids. add them
            for (Integer timeslotId : timeslots)
            {
                Timeslot timeslotToAdd = timeslotRepository.findByTimeSlotID(timeslotId);
                if (timeslotToAdd != null)
                {
                    apptToModify.addTimeslot(timeslotToAdd);
                }
            }
        }

        apptToModify.setCustomer(customerRepository.findByScrsUserId(appt.getCustomerId()));

        appointmentRepository.save(apptToModify);
        return apptToModify;
    }

    @Transactional
    public Appointment deleteAppointment(Appointment appt)
    {
        appointmentRepository.delete(appt);
        return appt;
    }
}
