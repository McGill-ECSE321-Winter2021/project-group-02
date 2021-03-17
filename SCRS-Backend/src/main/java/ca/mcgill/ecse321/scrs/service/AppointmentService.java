package ca.mcgill.ecse321.scrs.service;

import ca.mcgill.ecse321.scrs.dao.AppointmentRepository;
import ca.mcgill.ecse321.scrs.dao.CustomerRepository;
import ca.mcgill.ecse321.scrs.dao.TimeslotRepository;
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
    public Appointment modifyAppointment(Appointment appt)
    {
        if (appt == null) throw new IllegalArgumentException("Invalid appointment");
        if (appointmentRepository.findByAppointmentID(appt.getAppointmentID()) == null) throw new IllegalArgumentException("No such appointment exists");
        if (appt.getAppointmentType() == null) throw new IllegalArgumentException("Invalid appointment type.");
        if (appt.getCustomer() == null) throw new IllegalArgumentException("Invalid customer.");
        if (appt.getTimeslots() == null || appt.getTimeslots().size() == 0) throw new IllegalArgumentException("No valid timeslots selected.");
        if (appt.getRating() > 10 || appt.getRating() < 0) throw new IllegalArgumentException("Invalid rating");
        appointmentRepository.save(appt);
        return appt;
    }

    @Transactional
    public Appointment deleteAppointment(Appointment appt)
    {
        appointmentRepository.delete(appt);
        return appt;
    }
}
