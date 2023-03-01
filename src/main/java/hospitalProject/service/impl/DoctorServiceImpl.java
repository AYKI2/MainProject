package hospitalProject.service.impl;

import hospitalProject.model.Appointment;
import hospitalProject.model.Department;
import hospitalProject.model.Doctor;
import hospitalProject.model.Hospital;
import hospitalProject.repository.AppointmentRepository;
import hospitalProject.repository.DepartmentRepository;
import hospitalProject.repository.DoctorRepository;
import hospitalProject.repository.HospitalRepository;
import hospitalProject.service.DoctorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository repository;
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;
    @Autowired
    public DoctorServiceImpl(DoctorRepository repository, DepartmentRepository departmentRepository, HospitalRepository hospitalRepository, AppointmentRepository appointmentRepository) {
        this.repository = repository;
        this.departmentRepository = departmentRepository;
        this.hospitalRepository = hospitalRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Doctor> getAll(Long id) {
        return repository.getAll(id);
    }

    @Override
    public List<Doctor> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Long id, Doctor doctor) {
        try {
            for (int i = 0; i < doctor.getDepartmentsId().size(); i++) {
                doctor.getDepartments().add(departmentRepository.findById(doctor.getDepartmentsId().get(i)));
            }
            repository.save(id, doctor);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Doctor newDoctor) {
        try {
            repository.update(id, newDoctor);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            Doctor doctor = repository.findById(id);
            List<Appointment> appointments = doctor.getAppointments();

            List<Doctor> doctors = doctor.getHospital().getDoctors();
            doctors.removeIf(d->d.getId().equals(id));

            if(appointments != null) {
                List<Appointment> appointmentList = appointments.stream().filter(x -> x.getDoctor().getId().equals(id)).toList();
                appointmentList.forEach(d -> appointmentRepository.delete(d.getId()));
            }
            repository.delete(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Doctor findById(Long id) {
        try {
            return repository.findById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Appointment> getAppointmentsById(Long doctorId) {
        try {
            return repository.getAppointmentsById(doctorId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
