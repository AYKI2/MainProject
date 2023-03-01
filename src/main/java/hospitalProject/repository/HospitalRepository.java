package hospitalProject.repository;

import hospitalProject.model.Hospital;

import java.util.List;

public interface HospitalRepository {
    List<Hospital> getAll();
    void save(Hospital hospital);
    void update(Long id, Hospital newHospital);
    void delete(Long id);
    Hospital findById(Long id);
}
