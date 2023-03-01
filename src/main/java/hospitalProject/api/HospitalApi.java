package hospitalProject.api;

import hospitalProject.model.Hospital;
import hospitalProject.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hospital")
public class HospitalApi {
    private final HospitalService service;
    @Autowired
    public HospitalApi(HospitalService service) {this.service = service;}
    @GetMapping
    public String getAll(Model model){
        model.addAttribute("hospitals",service.getAll());
        return "hospital/hospital";
    }
    @GetMapping("/details/{hospitalId}")
    public String details(@PathVariable("hospitalId") Long hospitalId, Model model){
        model.addAttribute("hospital",service.findById(hospitalId));
        return "hospital/details";
    }
    @GetMapping("/new")
    public String createHospital(Model model){
        model.addAttribute("newHospital",new Hospital());
        return "hospital/newHospital";
    }
    @PostMapping("/save")
    public String saveHospital(@ModelAttribute("newHospital") @Valid Hospital hospital,
                               BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "hospital/newHospital";
        }
        try{
            service.save(hospital);
            return "redirect:/hospital";
        }catch (DataIntegrityViolationException e){
            model.addAttribute("hospitalName", "This hospital already exists in the database!");
            return "hospital/newHospital";
        }
    }
    @DeleteMapping("/delete/{hospitalId}")
    public String deleteHospital(@PathVariable("hospitalId")Long id){
        service.delete(id);
        return "redirect:/hospital";
    }
    @GetMapping("/{hospitalId}/getHospital")
    public String getHospital(Model model,@PathVariable("hospitalId") Long id){
        model.addAttribute("hospital",service.findById(id));
        return "hospital/update";
    }
    @PutMapping("/{hospitalId}/update")
    public String updateHospital(@PathVariable("hospitalId") Long hospitalId,
                                 @ModelAttribute("newHospital") @Valid Hospital newHospital,
                                 BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            return "hospital/update";
        }
        try{
            service.update(hospitalId,newHospital);
            return "redirect:/hospital";
        }catch (DataIntegrityViolationException e){
            model.addAttribute("oldHospital",service.findById(hospitalId));
            model.addAttribute("hospitalName", "This hospital already exists in the database!");
            return "redirect:hospital/update";
        }
    }
}
