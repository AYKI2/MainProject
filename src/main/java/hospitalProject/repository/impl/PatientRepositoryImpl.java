package hospitalProject.repository.impl;

import hospitalProject.model.Hospital;
import hospitalProject.model.Patient;
import hospitalProject.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class PatientRepositoryImpl implements PatientRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Patient> getAll(Long id) {
        try {
            return entityManager
                    .createQuery("from Patient p join p.hospital h where h.id = :id", Patient.class)
                    .setParameter("id", id)
                    .getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Long hospitalId, Patient patient) {
        try {
            Hospital hospital = entityManager.createQuery("from Hospital where id = :id", Hospital.class)
                    .setParameter("id", hospitalId)
                    .getSingleResult();
            patient.setHospital(hospital);
            hospital.getPatients().add(patient);
            entityManager.persist(hospital);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Patient newPatient) {
        try {
            Patient patient = entityManager.find(Patient.class, id);
            patient.setFirstName(newPatient.getFirstName());
            patient.setLastName(newPatient.getLastName());
            patient.setPhoneNumber(newPatient.getPhoneNumber());
            patient.setGender(newPatient.getGender());
            patient.setEmail(newPatient.getEmail());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
//        entityManager.remove(entityManager.find(Patient.class,id));
        try {
            entityManager.remove(entityManager.find(Patient.class,id));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Patient findById(Long id) {
        try {
            return entityManager.createQuery("from Patient where id = :id", Patient.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
