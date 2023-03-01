package hospitalProject.api;

import hospitalProject.model.Department;
import hospitalProject.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{id}/department")
public class DepartmentApi {
    private final DepartmentService service;
    @Autowired
    public DepartmentApi(DepartmentService service) {this.service = service;}
    @GetMapping
    public String getAll(Model model, @PathVariable("id") Long id){
        model.addAttribute("departments",service.getAll(id));
        return "department/department";
    }
    @GetMapping("/new")
    public String create(Model model, @PathVariable("id") Long id){
        model.addAttribute("newDepartment",new Department());
        model.addAttribute("hospitalId",id);
        return "department/newDepartment";
    }
    @PostMapping("/save")
    public String save(@PathVariable("id") Long departmentId,
                       @ModelAttribute("newDepartment") @Valid Department department,
                       BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            return "department/newDepartment";
        }
        try{
            service.save(departmentId,department);
            return "redirect:/{id}/department";
        }catch (DataIntegrityViolationException e){
            model.addAttribute("error","Department already exist!");
            model.addAttribute(departmentId);
            return "department/newDepartment";
        }catch (RuntimeException e){
            model.addAttribute("imageError", "The 'image' field must not be empty!");
            model.addAttribute(departmentId);
            return "department/newDepartment";
        }

    }
    @DeleteMapping("/{departmentId}/delete")
    public String deletePatient(@PathVariable("departmentId") Long departmentId,
                                @PathVariable("id") Long id){
        service.delete(departmentId);
        return "redirect:/{id}/department";
    }
    @GetMapping("/{departmentId}/get")
    private String getUpdateForm(@PathVariable("departmentId") Long departmentId,
                                 Model model,
                                 @PathVariable("id") Long id){
        model.addAttribute("department", service.findById(departmentId));
        return "department/update";
    }
    @PatchMapping("/{departmentId}")
    private String updatePatient(@PathVariable("id") Long id,
                                 @PathVariable("departmentId") Long departmentId,
                                 @ModelAttribute("department") @Valid Department department,
                                 BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("department", department);
            return "department/update";
        }
        try {
            service.update(departmentId, department);
            return "redirect:/{id}/department";
        }catch (DataIntegrityViolationException e){
            model.addAttribute("department", service.findById(departmentId));
            model.addAttribute(id);
            model.addAttribute("error","Department already exist!");
            return "department/update";
        }catch (RuntimeException e){
            model.addAttribute("imageError", "The 'image' field must not be empty!");
            model.addAttribute(departmentId);
            return "department/newDepartment";
        }
    }
}
