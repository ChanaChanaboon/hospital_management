package com.example.hospital_management.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.PatientRepository;
//import java.util.Optional; // For optional handling

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // Get all patients
    // @GetMapping
    // public String getAllPatients(Model model) {
    //     model.addAttribute("patients", patientRepository.findAll());
    //     return "patients"; // Thymeleaf template (patients.html)
    // }

    // Get patient by ID
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable int id) {
        return patientRepository.findById(id).orElse(null);
    }

    // Add a new patient
    @PostMapping
    public String addPatient(@ModelAttribute Patient patient, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "new_patient"; // Redirect back to the form if validation fails
        }
        patientRepository.save(patient); // Save the patient to the database
        redirectAttributes.addFlashAttribute("message", "Patient added successfully!");
        return "redirect:/patients"; // Redirect to the patients list
    }

    // Update an existing patient
    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable int id, @RequestBody Patient updatedPatient) {
        return patientRepository.findById(id).map(patient -> {
            patient.setName(updatedPatient.getName());
            patient.setAge(updatedPatient.getAge());
            patient.setGender(updatedPatient.getGender());
            patient.setAddress(updatedPatient.getAddress());
            patient.setContact(updatedPatient.getContact());
            patient.setAdmissionDate(updatedPatient.getAdmissionDate());
            return patientRepository.save(patient);
        }).orElse(null);
    }

    // Delete a patient
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            patientRepository.deleteById(id); // Perform the deletion
            redirectAttributes.addFlashAttribute("message", "Patient deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unable to delete patient. " + e.getMessage());
        }
        return "redirect:/patients"; // Redirect to the patients list page
    }


    // Show the form for adding a new patient
    @GetMapping("/new")
    public String newPatientForm(Model model) {
        model.addAttribute("patient", new Patient()); // Add a blank Patient object
        return "new_patient"; // Name of the Thymeleaf template for the form
    }
    @GetMapping("/{id}/edit")
    public String editPatientForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            redirectAttributes.addFlashAttribute("error", "Patient not found!");
            return "redirect:/patients"; // Redirect to the patients list if the ID is invalid
        }
        model.addAttribute("patient", patient); // Add the patient object to the model
        return "edit_patient"; // Name of the Thymeleaf template for editing
    }
    @PostMapping("/{id}/edit")
    public String updatePatient(@PathVariable int id, @ModelAttribute Patient updatedPatient, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit_patient"; // Return to the form if validation errors exist
        }

        patientRepository.findById(id).map(patient -> {
            patient.setName(updatedPatient.getName());
            patient.setAge(updatedPatient.getAge());
            patient.setGender(updatedPatient.getGender());
            patient.setAddress(updatedPatient.getAddress());
            patient.setContact(updatedPatient.getContact());
            patient.setAdmissionDate(updatedPatient.getAdmissionDate());
            return patientRepository.save(patient);
        }).orElse(null);

        redirectAttributes.addFlashAttribute("message", "Patient updated successfully!");
        return "redirect:/patients"; // Redirect to the patients list
    }
    @GetMapping
    public String listPatients(@RequestParam(value = "searchBy", required = false) String searchBy,
                            @RequestParam(value = "searchValue", required = false) String searchValue,
                            Model model) {
        List<Patient> patients;

        if (searchBy != null && searchValue != null) {
            if (searchBy.equalsIgnoreCase("id")) {
                try {
                    int id = Integer.parseInt(searchValue);
                    patients = patientRepository.findById(id)
                                .map(Collections::singletonList)
                                .orElse(Collections.emptyList());
                } catch (NumberFormatException e) {
                    patients = Collections.emptyList(); // Return empty if ID is invalid
                }
            } else if (searchBy.equalsIgnoreCase("name")) {
                patients = patientRepository.findByNameContainingIgnoreCase(searchValue);
            } else {
                patients = patientRepository.findAll();
            }
        } else {
            patients = patientRepository.findAll();
        }

        model.addAttribute("patients", patients);
        return "patients";
    }


}
