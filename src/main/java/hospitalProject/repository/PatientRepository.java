package hospitalProject.repository;

import hospitalProject.model.Patient;

import java.util.List;

public interface PatientRepository {
    List<Patient> getAll(Long id);
    void save(Long hospitalId,Patient patient);
    void update(Long id, Patient newPatient);
    void delete(Long id);
    Patient findById(Long id);
}
