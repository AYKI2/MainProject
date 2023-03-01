package hospitalProject.service;

import hospitalProject.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAll(Long id);
    List<Department> getAll();
    void save(Long hospitalId, Department department);
    void update(Long id, Department newDepartment);
    void delete(Long id);
    Department findById(Long id);
}
