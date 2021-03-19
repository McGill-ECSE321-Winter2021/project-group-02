package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.Appointment;
import ca.mcgill.ecse321.scrs.model.Customer;
import ca.mcgill.ecse321.scrs.model.Timeslot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer>
{

    Appointment findByAppointmentID(int appointmentID);

    Appointment findByTimeslotsContains(Timeslot timeslot);

    List<Appointment> findAppointmentsByAppointmentType(Appointment.AppointmentType type);

    List<Appointment> findAppointmentsByPaid(boolean aPaid);

    List<Appointment> findAppointmentsByCustomer(Customer customer);

    List<Appointment> findAppointmentsByTimeslots(Timeslot timeslots);

    boolean existsByTimeslots(Timeslot timeslot);
}
