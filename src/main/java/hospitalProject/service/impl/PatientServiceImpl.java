package hospitalProject.service.impl;

import hospitalProject.model.Appointment;
import hospitalProject.model.Hospital;
import hospitalProject.model.Patient;
import hospitalProject.repository.AppointmentRepository;
import hospitalProject.repository.HospitalRepository;
import hospitalProject.repository.PatientRepository;
import hospitalProject.service.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;
    @Autowired
    public PatientServiceImpl(PatientRepository repository, HospitalRepository hospitalRepository, AppointmentRepository appointmentRepository) {
        this.repository = repository;
        this.hospitalRepository = hospitalRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Patient> getAll(Long id) {
        try{
            return repository.getAll(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Long hospitalId,Patient patient) {
        try {
            repository.save(hospitalId, patient);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Patient newPatient) {
        try {
            repository.update(id, newPatient);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Patient patient = repository.findById(id);
            List<Appointment> appointments = patient.getAppointments();
            List<Patient> patients = patient.getHospital().getPatients();
            if (appointments != null) {
                List<Appointment> appointmentList = appointments.stream().filter(x -> x.getPatient().getId().equals(id)).toList();
                appointmentList.forEach(p->appointmentRepository.delete(p.getId()));
            }
            patients.removeIf(p->p.getId().equals(id));
            repository.delete(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Patient findById(Long id) {
        try {
            return repository.findById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
