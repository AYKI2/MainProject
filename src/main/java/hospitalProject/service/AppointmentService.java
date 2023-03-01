package hospitalProject.service;

import hospitalProject.model.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAll(Long hospitalId);
    void save(Long hospitalId, Appointment appointment);
    void update(Long id, Appointment newAppointment);
    void delete(Long id);
    Appointment findById(Long id);
    List<Appointment> getDoctorAppointments(Long doctorId);
}
