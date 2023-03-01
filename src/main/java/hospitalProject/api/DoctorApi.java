package hospitalProject.api;

import hospitalProject.model.Doctor;
import hospitalProject.service.DepartmentService;
import hospitalProject.service.DoctorService;
import hospitalProject.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{id}/doctor")
public class DoctorApi {
    private final DoctorService service;
    private final DepartmentService departmentService;
    private final PatientService patientService;
    @Autowired
    public DoctorApi(DoctorService service, DepartmentService departmentService, PatientService patientService) {this.service = service;
        this.departmentService = departmentService;
        this.patientService = patientService;
    }
    @GetMapping
    public String getAll(Model model, @PathVariable("id") Long id){
        model.addAttribute("doctors",service.getAll(id));
        return "doctor/doctor";
    }
    @GetMapping("/new")
    public String create(Model model, @PathVariable("id") Long id){
        model.addAttribute("newDoctor",new Doctor());
        model.addAttribute("departments",departmentService.getAll(id));
        return "doctor/newDoctor";
    }
    @PostMapping("/save")
    public String save(@PathVariable("id") Long doctorId,
                       @ModelAttribute("newDoctor") @Valid Doctor doctor,
                       BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAll(doctorId));
            return "doctor/newDoctor";
        }
        try {
            service.save(doctorId,doctor);
            return "redirect:/{id}/doctor";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("hospitalId", doctorId);
            model.addAttribute("departments", departmentService.getAll(doctorId));
            model.addAttribute("email", "This email already exists in the database!");
            return "doctor/newDoctor";
        }
    }
    @DeleteMapping("/{doctorId}/delete")
    public String deletePatient(@PathVariable("doctorId") Long doctorId,
                                @PathVariable("id") Long id){
        service.delete(doctorId);
        return "redirect:/{id}/doctor";
    }
    @GetMapping("/{doctorId}/get")
    private String getUpdateForm(@PathVariable("doctorId") Long doctorId,
                                 Model model,
                                 @PathVariable("id") Long id){
        model.addAttribute("doctor", service.findById(doctorId));
        model.addAttribute("departments",departmentService.getAll(id));
        return "doctor/update";
    }
    @PatchMapping("/{doctorId}")
    private String updatePatient(@PathVariable("id") Long id,
                                 @PathVariable("doctorId") Long doctorId,
                                 @ModelAttribute("patient") @Valid Doctor doctor,
                                 BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("doctor", doctor);
            model.addAttribute("departments", departmentService.getAll(id));
            return "department/update";
        }
        try{
            service.update(doctorId, doctor);
            return "redirect:/{id}/doctor";
        }catch (DataIntegrityViolationException e){
            model.addAttribute("departments", departmentService.getAll(id));
            model.addAttribute("departmentsId", "This department already exist");
            model.addAttribute("email", "This email already exists in the database!");
            return "department/update";
        }
    }
    @GetMapping("/{doctorId}/entries")
    private String getEntries(@PathVariable("doctorId") Long doctorId,
                                 Model model,
                                 @PathVariable("id") Long id){
        model.addAttribute("doctor", service.getAppointmentsById(doctorId));
        return "appointment/doctorAppointments";
    }
}
