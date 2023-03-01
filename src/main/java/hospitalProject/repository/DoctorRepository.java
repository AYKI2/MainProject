package hospitalProject.repository;

import hospitalProject.model.Appointment;
import hospitalProject.model.Doctor;

import java.util.List;

public interface DoctorRepository {
    List<Doctor> getAll(Long hospitalId);
    List<Doctor> getAll();
    void save(Long hospitalId,Doctor doctor);
    void update(Long id, Doctor newDoctor);
    void delete(Long id);
    Doctor findById(Long id);
    List<Appointment> getAppointmentsById(Long doctorId);
}
