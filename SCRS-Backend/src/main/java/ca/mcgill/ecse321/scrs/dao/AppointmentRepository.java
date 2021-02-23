package ca.mcgill.ecse321.scrs.dao;

import ca.mcgill.ecse321.scrs.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer>
{

    Appointment findByAppointmentID(int appointmentID);

    List<Appointment> findAppointmentsByAppointmentType(Appointment.AppointmentType type);

    List<Appointment> findAppointmentsByPaid(boolean aPaid);

    List<Appointment> findAppointmentByCustomer(Customer customer);
}
