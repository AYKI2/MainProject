package hospitalProject.repository.impl;

import hospitalProject.model.Appointment;
import hospitalProject.model.Doctor;
import hospitalProject.model.Hospital;
import hospitalProject.repository.DoctorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public class DoctorRepositoryImpl implements DoctorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Doctor> getAll(Long id) {
        try {
            return entityManager
                    .createQuery("from Doctor d join d.hospital h where h.id = :id", Doctor.class)
                    .setParameter("id", id)
                    .getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
    @Override
    public List<Doctor> getAll() {
        try {
            return entityManager
                    .createQuery("SELECT d FROM Doctor d", Doctor.class)
                    .getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Long hospitalId,Doctor doctor) {
        try {
            Hospital hospital = entityManager.createQuery("from Hospital where id = :id", Hospital.class)
                    .setParameter("id", hospitalId)
                    .getSingleResult();
            hospital.getDoctors().add(doctor);
            doctor.setHospital(hospital);
            entityManager.persist(doctor);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Doctor newDoctor) {
        try {
            Doctor doctor = entityManager.find(Doctor.class, id);
            doctor.setFirstName(newDoctor.getFirstName());
            doctor.setLastName(newDoctor.getLastName());
            doctor.setPosition(newDoctor.getPosition());
            doctor.setEmail(newDoctor.getEmail());
            doctor.setImage(newDoctor.getImage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            entityManager.remove(entityManager.find(Doctor.class,id));
//             entityManager.createQuery("select d from Doctor d join d.hospital h where h.id = :id", Doctor.class)
//                    .setParameter("id", id).getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Doctor findById(Long id) {
        try {
            return entityManager.find(Doctor.class, id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Appointment> getAppointmentsById(Long doctorId) {
        try {
            return entityManager.find(Doctor.class, doctorId).getAppointments();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
