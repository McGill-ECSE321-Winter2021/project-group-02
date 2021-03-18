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

import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse321.scrs.service.ServiceHelpers.toList;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    TimeslotRepository timeslotRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Appointment createAppointment(Appointment.AppointmentType appointmentType, String service, String note, boolean paid, Customer customer, Timeslot... timeslots) {
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
        return new ArrayList<>(appointmentRepository.findAppointmentsByCustomer(customer));
    }

    @Transactional
    public List<Appointment> getAppointmentsByTimeslot(Timeslot timeslot) {
        return new ArrayList<>(appointmentRepository.findAppointmentsByTimeslots(timeslot));
    }

    @Transactional
    public Appointment rateAppointment(int appointmentId, int rating) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment == null) throw new IllegalArgumentException("No such appointment!");
        appointment.setRating(rating);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Transactional
    public Appointment modifyAppointment(AppointmentDto appt)
    {
        if (appt == null) throw new IllegalArgumentException("Invalid appointment");
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
}
