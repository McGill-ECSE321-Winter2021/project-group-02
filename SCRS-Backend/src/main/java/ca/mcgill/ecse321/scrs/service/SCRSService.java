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
public class SCRSService {
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
        return new ArrayList<>(appointmentRepository.findAppointmentsByCustomer(customer));
    }

    @Transactional
    public List<Appointment> getAppointmentsByTimeslots(List<Timeslot> timeslots) {
        return new ArrayList<>(appointmentRepository.findAppointmentsByTimeslots(timeslots));
    }

    @Transactional
    public List<Appointment> getAppointmentsByTimeslotsAndCustomer(List<Timeslot> timeslots, Customer customer) {
        return new ArrayList<>(appointmentRepository.findAppointmentsByTimeslotsAndCustomer(timeslots, customer));
    }

    @Transactional
    public Assistant createAssistant(String email, String name, String password, String phone) {
        Assistant assistant = new Assistant();
        assistant.setEmail(email);
        assistant.setName(name);
        assistant.setPassword(password);
        assistant.setPhone(phone);
        assistantRepository.save(assistant);
        return assistant;
    }

    @Transactional
    public List<Assistant> getAllAssistants() {
        return toList(assistantRepository.findAll());
    }

    @Transactional
    public Assistant getAssistantByEmail(String email) {
        return assistantRepository.findByEmail(email);
    }

    @Transactional
    public Assistant getAssistantByName(String name) {
        return assistantRepository.findByName(name);
    }

    @Transactional
    public Assistant getAssistantByPhone(String phone) {
        return assistantRepository.findByPhone(phone);
    }

    @Transactional
    public Customer createCustomer(String email, String name, String password, String phone) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setName(name);
        customer.setPassword(password);
        customer.setPhone(phone);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return toList(customerRepository.findAll());
    }

    @Transactional
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public Customer getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    @Transactional
    public Customer getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Transactional
    public Customer getCustomerByAppointments(List<Appointment> appointments) {
        return customerRepository.findByAppointments(appointments);
    }

    @Transactional
    public Technician createTechnician(String email, String name, String password, String phone) {
        Technician technician = new Technician();
        technician.setEmail(email);
        technician.setName(name);
        technician.setPassword(password);
        technician.setPhone(phone);
        technicianRepository.save(technician);
        return technician;
    }

    @Transactional
    public List<Technician> getAllTechnicians() {
        return toList(technicianRepository.findAll());
    }

    @Transactional
    public Technician getTechnicianByEmail(String email) {
        return technicianRepository.findByEmail(email);
    }

    @Transactional
    public Technician getTechnicianByName(String name) {
        return technicianRepository.findByName(name);
    }

    @Transactional
    public Technician getTechnicianByPhone(String phone) {
        return technicianRepository.findByPhone(phone);
    }

    @Transactional
    public Technician getTechnicianByAvailabilities(List<Timeslot> availabilities) {
        return technicianRepository.findByAvailabilities(availabilities);
    }

    //TODO add more as necessary...

    //---------------- HELPERS ------------------------

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

}