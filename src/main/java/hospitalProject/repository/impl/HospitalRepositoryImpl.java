package hospitalProject.repository.impl;

import hospitalProject.model.Hospital;
import hospitalProject.repository.HospitalRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public class HospitalRepositoryImpl implements HospitalRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Hospital> getAll() {
        try {
            return entityManager.createQuery("SELECT h FROM Hospital h", Hospital.class).getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Hospital hospital) {
        try {
            entityManager.persist(hospital);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        try {
            Hospital hospital = entityManager.find(Hospital.class, id);
            hospital.setName(newHospital.getName());
            hospital.setAddress(newHospital.getAddress());
            hospital.setImage(newHospital.getImage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            entityManager.remove(entityManager.find(Hospital.class,id));
//            entityManager.createQuery("delete from Hospital h where h.id = :id")
//                    .setParameter("id", id).executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Hospital findById(Long id) {
        try {
            return entityManager.find(Hospital.class, id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
