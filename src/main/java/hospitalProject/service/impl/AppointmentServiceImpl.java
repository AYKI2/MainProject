package hospitalProject.service.impl;

import hospitalProject.model.*;
import hospitalProject.repository.*;
import hospitalProject.service.AppointmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository repository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository, HospitalRepository hospitalRepository, PatientRepository patientRepository, DepartmentRepository departmentRepository, DoctorRepository doctorRepository) {
        this.repository = repository;
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Appointment> getAll(Long hospitalId) {
        try{
            return repository.getAll(hospitalId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Long hospitalId, Appointment appointment) {
        try {
            Appointment appointment1 = new Appointment();
            Department department = departmentRepository.findById(appointment.getDepartmentId());
            Doctor doctor = doctorRepository.findById(appointment.getDoctorId());
            Patient patient = patientRepository.findById(appointment.getPatientId());
            appointment1.setDate(appointment.getDate());
            appointment1.setDoctor(doctor);
            appointment1.setPatient(patient);
            appointment1.setDepartment(department);
            Hospital hospital = hospitalRepository.findById(hospitalId);
            hospital.getAppointments().add(appointment1);
            repository.save(hospitalId, appointment1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Appointment newAppointment) {
        try {
            repository.update(id, newAppointment);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            repository.delete(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Appointment findById(Long id) {
        try {
            return repository.findById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        try {
            return repository.getDoctorAppointments(doctorId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
