package hospitalProject.repository.impl;

import hospitalProject.model.Appointment;
import hospitalProject.model.Department;
import hospitalProject.model.Hospital;
import hospitalProject.repository.DepartmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DepartmentRepositoryImpl implements DepartmentRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Department> getAll(Long id) {
        try {
            return entityManager
                    .createQuery("from Department d join d.hospital h where h.id = :id", Department.class)
                    .setParameter("id", id)
                    .getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Department> getAll() {
        try {
            return entityManager
                    .createQuery("select d from Department d", Department.class)
                    .getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Long hospitalId, Department department) {
        try {
            Hospital hospital = entityManager.createQuery("from Hospital where id = :id", Hospital.class)
                    .setParameter("id", hospitalId)
                    .getSingleResult();
            department.setHospital(hospital);
            hospital.getDepartments().add(department);
            entityManager.persist(hospital);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Department newDepartment) {
        try {
            Department department = entityManager.find(Department.class, id);
            department.setId(department.getId());
            department.setName(newDepartment.getName());
            department.setImage(newDepartment.getImage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            entityManager.remove(entityManager.find(Department.class, id));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Department findById(Long id) {
        try {
            return entityManager.find(Department.class, id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
