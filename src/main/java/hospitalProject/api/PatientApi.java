package hospitalProject.api;

import hospitalProject.model.Patient;
import hospitalProject.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{id}/patient")
public class PatientApi {
    private final PatientService service;
    @Autowired
    public PatientApi(PatientService service) {this.service = service;}
    @GetMapping
    public String getAll(Model model, @PathVariable("id") Long id){
        model.addAttribute("patients",service.getAll(id));
        return "patient/patient";
    }
    @GetMapping("/new")
    public String create(Model model, @PathVariable("id") Long id){
        model.addAttribute("newPatient",new Patient());
        return "patient/newPatient";
    }
    @PostMapping("/save")
    public String save(@PathVariable("id") Long patientId,
                       @ModelAttribute("newPatient") @Valid Patient patient,
                       BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "patient/newPatient";
        }
        try {
            service.save(patientId,patient);
            return "redirect:/{id}/patient";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("email", "This email already exists in the database");
            return "patient/newPatient";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Phone number already exist!");
            model.addAttribute(patientId);
            return "patient/newPatient";
        }

    }
    @DeleteMapping("/{patientId}/delete")
    public String deletePatient(@PathVariable("patientId") Long patientId,
                                @PathVariable("id") Long id){
        service.delete(patientId);
        return "redirect:/{id}/patient";
    }
    @GetMapping("/{patientId}/get")
    private String getUpdateForm(@PathVariable("patientId") Long patientId,
                                 Model model,
                                 @PathVariable("id") Long id){
        model.addAttribute("patient", service.findById(patientId));
        return "patient/update";
    }
    @PatchMapping("/{patientId}")
    private String updatePatient(@PathVariable("id") Long id,
                                 @PathVariable("patientId") Long patientId,
                                 @ModelAttribute("patient") @Valid Patient patient,
                                 BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patient);
            model.addAttribute(id);
            return "patient/update";
        }
        try {
            service.update(patientId, patient);
            return "redirect:/{id}/patient";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("patient", service.findById(patientId));
            model.addAttribute(id);
            model.addAttribute("email", "This email already exists in the database");
            return "patient/update";
        } catch (RuntimeException e) {
            model.addAttribute("patient", service.findById(patientId));
            model.addAttribute(id);
            model.addAttribute("error", "Phone number already exist!");
            return "patient/update";
        }
    }
}
