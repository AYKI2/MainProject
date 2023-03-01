package hospitalProject.service.impl;

import hospitalProject.model.Appointment;
import hospitalProject.model.Department;
import hospitalProject.model.Doctor;
import hospitalProject.model.Hospital;
import hospitalProject.repository.AppointmentRepository;
import hospitalProject.repository.DepartmentRepository;
import hospitalProject.repository.DoctorRepository;
import hospitalProject.repository.HospitalRepository;
import hospitalProject.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;
    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository, DoctorRepository doctorRepository, HospitalRepository hospitalRepository, AppointmentRepository appointmentRepository) {
        this.repository = repository;
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Department> getAll(Long id) {
        try{
            return repository.getAll(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
    public List<Department> getAll() {
        try {
            return repository.getAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Long id, Department department) {
        try {
            repository.save(id, department);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Department newDepartment) {
        try {
            repository.update(id, newDepartment);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            Department department = repository.findById(id);
            List<Hospital> hospitals = hospitalRepository.getAll();
            List<Doctor> doctors = department.getDoctors();
            for (int i = 0; i < doctors.size(); i++) {
                List<Department> departments = doctors.get(i).getDepartments();
                departments.removeIf(x->x.getId().equals(id));
            }
            for (Hospital hospital : hospitals) {
                List<Appointment> appointments = hospital.getAppointments();
                if (appointments != null) {
                    List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
                    appointmentList.forEach(s -> appointmentRepository.delete(s.getId()));
                }
            }

            List<Department> departments = department.getHospital().getDepartments();
            departments.removeIf(d -> d.getId().equals(id));
            repository.delete(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Department findById(Long id) {
        try {
            return repository.findById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
