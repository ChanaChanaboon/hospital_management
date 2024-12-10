package com.example.hospital_management.controller;

import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.hospital_management.repository.DepartmentRepository;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public String listDoctors(@RequestParam(value = "searchBy", required = false) String searchBy,
                              @RequestParam(value = "searchValue", required = false) String searchValue,
                              Model model) {
        List<Doctor> doctors;

        if (searchBy != null && searchValue != null) {
            if (searchBy.equalsIgnoreCase("id")) {
                try {
                    int id = Integer.parseInt(searchValue);
                    doctors = doctorRepository.findById(id)
                                .map(Collections::singletonList)
                                .orElse(Collections.emptyList());
                } catch (NumberFormatException e) {
                    doctors = Collections.emptyList(); // Return empty if ID is invalid
                }
            } else if (searchBy.equalsIgnoreCase("name")) {
                doctors = doctorRepository.findByNameContainingIgnoreCase(searchValue);
            } else {
                doctors = doctorRepository.findAll();
            }
        } else {
            doctors = doctorRepository.findAll();
        }

        model.addAttribute("doctors", doctors);
        return "doctors";
    }

    @GetMapping("/new")
    public String newDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", departmentRepository.findAll());
        return "new_doctor";
    }

    @PostMapping
    public String addDoctor(@ModelAttribute Doctor doctor, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "new_doctor";
        }
        doctorRepository.save(doctor);
        redirectAttributes.addFlashAttribute("message", "Doctor added successfully!");
        return "redirect:/doctors";
    }

    @GetMapping("/{id}/edit")
    public String editDoctorForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if (doctor == null) {
            redirectAttributes.addFlashAttribute("error", "Doctor not found!");
            return "redirect:/doctors";
        }
        model.addAttribute("doctor", doctor);
        model.addAttribute("departments", departmentRepository.findAll());
        return "edit_doctor";
    }

    @PostMapping("/{id}/edit")
    public String updateDoctor(@PathVariable int id,
                            @ModelAttribute Doctor updatedDoctor,
                            @RequestParam int department, // ID of the selected department
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit_doctor";
        }

        doctorRepository.findById(id).ifPresent(doctor -> {
            doctor.setName(updatedDoctor.getName());
            doctor.setSpecialty(updatedDoctor.getSpecialty());
            doctor.setContact(updatedDoctor.getContact());
            doctor.setDepartment(departmentRepository.findById(department).orElse(null));
            doctorRepository.save(doctor);
        });

        redirectAttributes.addFlashAttribute("message", "Doctor updated successfully!");
        return "redirect:/doctors";
    }


    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            doctorRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Doctor deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unable to delete doctor. " + e.getMessage());
        }
        return "redirect:/doctors";
    }
}
