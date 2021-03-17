package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer>
{

    Appointment findByAppointmentID(int appointmentID);

    Appointment findByTimeslotsContains(Timeslot timeslot);

    List<Appointment> findAppointmentsByAppointmentType(Appointment.AppointmentType type);

    List<Appointment> findAppointmentsByPaid(boolean aPaid);

    List<Appointment> findAppointmentsByCustomer(Customer customer);
}
