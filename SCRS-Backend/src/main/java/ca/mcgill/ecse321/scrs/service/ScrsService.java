package ca.mcgill.ecse321.scrs.service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.scrs.dao.*;
import ca.mcgill.ecse321.scrs.model.*;

@Service
public class ScrsService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AssistantRepository assistantRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SCRSRepository scrsRepository;
    @Autowired
    SCRSUserRepository scrsUserRepository;
    @Autowired
    TechnicianRepository technicianRepository;
    @Autowired
    TimeslotRepository timeslotRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;

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
    public List<Appointment> getAppointmentsByCustomer(Customer customer) {
        List<Appointment> appointments = new ArrayList<>();
        appointments.addAll(appointmentRepository.findAppointmentsByCustomer(customer));
        return appointments;
    }

    @Transactional
    public List<Appointment> getAppointmentsByTimeslots(List<Timeslot> timeslots) {
        List<Appointment> appointments = new ArrayList<>();
        appointments.addAll(appointmentRepository.findAppointmentsByTimeslots(timeslots));
        return appointments;
    }

    @Transactional
    public List<Appointment> getAppointmentsByTimeslotsAndCustomer(List<Timeslot> timeslots, Customer customer) {
        List<Appointment> appointments = new ArrayList<>();
        appointments.addAll(appointmentRepository.findAppointmentsByTimeslotsAndCustomer(timeslots, customer));
        return appointments;
    }

    //TODO do more as necessary...

    //---------------- HELPERS ------------------------

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}