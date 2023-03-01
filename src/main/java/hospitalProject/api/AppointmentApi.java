package hospitalProject.api;

import hospitalProject.model.Appointment;
import hospitalProject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{id}/appointment")
public class AppointmentApi {
    private final AppointmentService service;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;
    @Autowired
    public AppointmentApi(AppointmentService service,
                          PatientService patientService,
                          DoctorService doctorService,
                          DepartmentService departmentService, HospitalService hospitalService) {
        this.service = service;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.departmentService = departmentService;
        this.hospitalService = hospitalService;
    }
    @GetMapping
    public String getAll(Model model, @PathVariable("id") Long id){
        model.addAttribute("appointments",service.getAll(id));
        return "appointment/appointment";
    }
    @GetMapping("/appNew")
    public String create(Model model, @PathVariable("id") Long id){
        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("doctors", doctorService.getAll(id));
        model.addAttribute("patients", patientService.getAll(id));
        model.addAttribute("departments", departmentService.getAll(id));
        return "appointment/newAppointment";
    }
    @PostMapping("/save")
    public String save(@PathVariable("id") Long id,
                       @ModelAttribute("newAppointment") Appointment appointment){
        service.save(id,appointment);
        return "redirect:/{id}/appointment";
    }
    @DeleteMapping("/{appointmentId}/delete")
    public String deleteAppointment(@PathVariable("appointmentId") Long appointmentId,
                                @PathVariable("id") Long id){
        service.delete(appointmentId);
        return "redirect:/{id}/appointment";
    }
    @GetMapping("/{appointmentId}/get")
    private String getUpdateForm(@PathVariable("appointmentId") Long appointmentId,
                                 Model model,
                                 @PathVariable("id") Long id){
        model.addAttribute("appointment", service.findById(id));
        model.addAttribute("patients", patientService.getAll(id));
        model.addAttribute("doctors", doctorService.getAll(id));
        model.addAttribute("departments", departmentService.getAll(id));
        model.addAttribute("hospitalId", id);
        model.addAttribute("appointment", service.findById(appointmentId));
        return "appointment/update";
    }
    @PatchMapping("/{appointmentId}")
    private String updateAppointment(@PathVariable("id") Long id,
                                 @PathVariable("appointmentId") Long appointmentId,
                                 @ModelAttribute("appointment") Appointment appointment){
        service.update(appointmentId, appointment);
        return "redirect:/{id}/appointment";
    }

    @GetMapping("/{doctorId}/entries")
    private String getDoctorAppointments(Model model,
                                         @PathVariable("id") Long id,
                                         @PathVariable("doctorId") Long doctorId){
        model.addAttribute("appointments", service.getDoctorAppointments(doctorId));
        return "appointment/doctorAppointments";
    }
//    @GetMapping("{doctorId}/newRecording")
//    public String createRecording(Model model,
//                                  @PathVariable("doctorId") Long doctorId,
//                                  @PathVariable("id") Long id){
//        model.addAttribute("newRecording", new Appointment());
//        model.addAttribute("doctor",doctorService.findById(doctorId));
//        model.addAttribute("departments", departmentService.getAll(id));
//        model.addAttribute("patients", patientService.getAll(id));
//        model.addAttribute("hospital", hospitalService.findById(id));
//        return "appointment/makingAnAppointment";
//    }
//    @PostMapping("{doctorId}/saveRecording")
//    public String saveRecording(@PathVariable("id") Long id,
//                                @PathVariable("doctorId") Long doctorId,
//                       @ModelAttribute("newRecording") Appointment appointment){
//        service.save(doctorId,appointment);
//        return "redirect:/{id}/doctor";
//    }
}
