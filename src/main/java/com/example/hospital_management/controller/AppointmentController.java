package com.example.hospital_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.repository.AppointmentRepository;
import com.example.hospital_management.repository.PatientRepository;
import com.example.hospital_management.repository.DoctorRepository;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Get all appointments
    // @GetMapping
    // public String getAllAppointments(Model model) {
    //     model.addAttribute("appointments", appointmentRepository.findAll());
    //     return "appointments"; // Thymeleaf template for appointments
    // }

    // Add a new appointment
    @GetMapping("/new")
    public String newAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        List<Patient> patients = patientRepository.findAll();
        List<Doctor> doctors = doctorRepository.findAll();

        if (patients == null || patients.isEmpty()) {
            model.addAttribute("error", "No patients found. Please add patients first.");
        } else {
            model.addAttribute("patients", patients);
        }

        if (doctors == null || doctors.isEmpty()) {
            model.addAttribute("error", "No doctors found. Please add doctors first.");
        } else {
            model.addAttribute("doctors", doctors);
        }

        return "new_appointment";
    }

    @PostMapping("/new")
    public String addAppointment(@ModelAttribute("appointment") Appointment appointment,
                                BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());

        if (result.hasErrors()) {
            return "new_appointment";
        }

        try {
            appointmentRepository.save(appointment);
        } catch (Exception e) {
            model.addAttribute("error", "Error saving appointment: " + e.getMessage());
            return "new_appointment";
        }

        redirectAttributes.addFlashAttribute("message", "Appointment added successfully!");
        return "redirect:/appointments";
    }
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            appointmentRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Appointment deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unable to delete appointment: " + e.getMessage());
        }
        return "redirect:/appointments";
    }

    @GetMapping("/{id}/edit")
    public String editAppointmentForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) {
            redirectAttributes.addFlashAttribute("error", "Appointment not found!");
            return "redirect:/appointments";
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctorRepository.findAll()); // Pass all doctors
        model.addAttribute("patients", patientRepository.findAll()); // Pass all patients
        model.addAttribute("statuses", List.of("Pending", "Completed", "Cancelled")); // Predefined statuses
        return "edit_appointment";
    }

    @PostMapping("/{id}/edit")
    public String updateAppointment(@PathVariable int id, @ModelAttribute Appointment updatedAppointment,
                                     @RequestParam("doctorID") int doctorID,
                                     @RequestParam("status") String status,
                                     @RequestParam("patientID") int patientID,
                                     BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit_appointment";
        }

        appointmentRepository.findById(id).ifPresent(appointment -> {
            appointment.setDate(updatedAppointment.getDate());
            appointment.setTime(updatedAppointment.getTime());
            appointment.setStatus(status); // Set the selected status
            doctorRepository.findById(doctorID).ifPresent(appointment::setDoctor); // Set the selected doctor
            patientRepository.findById(patientID).ifPresent(appointment::setPatient);
            appointmentRepository.save(appointment);
        });

        redirectAttributes.addFlashAttribute("message", "Appointment updated successfully!");
        return "redirect:/appointments";
    }
    @GetMapping
    public String listAppointments(@RequestParam(value = "searchBy", required = false) String searchBy,
                                    @RequestParam(value = "searchValue", required = false) String searchValue,
                                    Model model) {
        List<Appointment> appointments;

        // Perform search logic
        if (searchBy != null && searchValue != null) {
            if (searchBy.equalsIgnoreCase("id")) {
                try {
                    int id = Integer.parseInt(searchValue);
                    appointments = appointmentRepository.findById(id)
                                .map(Collections::singletonList)
                                .orElse(Collections.emptyList());
                } catch (NumberFormatException e) {
                    appointments = Collections.emptyList(); // Handle invalid ID input
                }
            } else if (searchBy.equalsIgnoreCase("name")) {
                appointments = appointmentRepository.findByPatientNameContainingIgnoreCase(searchValue);
            } else {
                // Invalid searchBy parameter, return empty list or default results
                appointments = Collections.emptyList();
            }
        } else {
            // Default case: load all appointments
            appointments = appointmentRepository.findAll();
        }

        // Handle cases where doctor is null
        model.addAttribute("appointments", appointments);
        return "appointments";
    }


}
