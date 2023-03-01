package hospitalProject.repository.impl;

import hospitalProject.model.Appointment;
import hospitalProject.model.Hospital;
import hospitalProject.repository.AppointmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class AppointmentRepositoryImpl implements AppointmentRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Appointment> getAll(Long hospitalId) {
        try {
            return entityManager
                    .createQuery("select a from Hospital h join h.appointments a where h.id = :id",
                            Appointment.class)
                    .setParameter("id", hospitalId)
                    .getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Long hospitalId, Appointment appointment) {
        try {
            entityManager.merge(appointment);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Appointment newAppointment) {
        try {
            Appointment appointment = entityManager.find(Appointment.class, id);
            appointment.setDate(newAppointment.getDate());
            appointment.setPatient(newAppointment.getPatient());
            appointment.setDoctor(newAppointment.getDoctor());
            appointment.setDepartment(newAppointment.getDepartment());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            List<Hospital> hospitalList = entityManager.createQuery("select h from Hospital h", Hospital.class).getResultList();
            hospitalList.forEach(x -> x.getAppointments().removeIf(h -> h.getId().equals(id)));
            entityManager.remove(entityManager.find(Appointment.class, id));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Appointment findById(Long id) {
        try {
            return entityManager.find(Appointment.class, id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        try {
            return entityManager
                    .createQuery("select a from Doctor d join d.appointments a where d.id = :id",
                            Appointment.class)
                    .setParameter("id", doctorId)
                    .getResultList();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
