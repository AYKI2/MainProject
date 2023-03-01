package hospitalProject.service.impl;

import hospitalProject.model.Hospital;
import hospitalProject.repository.HospitalRepository;
import hospitalProject.service.HospitalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    @Autowired
    public HospitalServiceImpl(HospitalRepository hospitalRepository) {this.hospitalRepository = hospitalRepository;}

    @Override
    public List<Hospital> getAll() {
        try {
            return hospitalRepository.getAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Hospital hospital) {
        try {
            hospitalRepository.save(hospital);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        try {
            hospitalRepository.update(id, newHospital);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            hospitalRepository.delete(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Hospital findById(Long id) {
        try {
            return hospitalRepository.findById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
