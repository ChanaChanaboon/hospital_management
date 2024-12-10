package com.example.hospital_management.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.hospital_management.entity.Department;
import com.example.hospital_management.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.hospital_management.repository.DoctorRepository;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DoctorRepository doctorRepository; 

    // Get all departments
    // @GetMapping
    // public String getAllDepartments(Model model) {
    //     model.addAttribute("departments", departmentRepository.findAll());
    //     return "departments"; // Thymeleaf template for departments
    // }


    // Get department by ID
    @GetMapping("/{id}")
    @ResponseBody

    public Department getDepartmentById(@PathVariable int id) {
        return departmentRepository.findById(id).orElse(null);
    }

    // Add a new department
    @PostMapping
    public String addDepartment(@ModelAttribute Department department, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "new_department"; // Return to the form if validation fails
        }
        departmentRepository.save(department);
        redirectAttributes.addFlashAttribute("message", "Department added successfully!");
        return "redirect:/departments";
}


    // Update an existing department
   @PostMapping("/{id}/edit")
    public String updateDepartment(@PathVariable int id, @ModelAttribute Department updatedDepartment, BindingResult result, RedirectAttributes redirectAttributes) {
        //System.out.println("Received ID for editing: " + id);

        if (result.hasErrors()) {
            return "edit_department"; // Validation errors redirect back to form
        }

        departmentRepository.findById(id).ifPresent(department -> {
            department.setName(updatedDepartment.getName());
            department.setFloor(updatedDepartment.getFloor());
            departmentRepository.save(department);
        });

        redirectAttributes.addFlashAttribute("message", "Department updated successfully!");
        return "redirect:/departments";
    }



    // Delete a department
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            //System.out.println("Redirecting to /departments after deleting ID: " + id);
            departmentRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Department deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unable to delete department: " + e.getMessage());
        }
        return "redirect:/departments";
    }



    @GetMapping("/new")
        public String newDepartmentForm(Model model) {
            model.addAttribute("department", new Department());
            return "new_department";
    }
   @GetMapping("/{id}/edit")
    public String editDepartmentForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        //System.out.println("Editing Department ID: " + id);

        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            redirectAttributes.addFlashAttribute("error", "Department not found!");
            return "redirect:/departments";
        }
        model.addAttribute("department", department); // Bind department object to model
        return "edit_department"; // Returns the correct Thymeleaf template
    }
    @GetMapping
    public String listDepartments(@RequestParam(value = "searchBy", required = false) String searchBy,
                                @RequestParam(value = "searchValue", required = false) String searchValue,
                                Model model) {
        List<Department> departments;

        if (searchBy != null && searchValue != null) {
            if (searchBy.equalsIgnoreCase("id")) {
                try {
                    int id = Integer.parseInt(searchValue);
                    departments = departmentRepository.findById(id)
                                .map(Collections::singletonList)
                                .orElse(Collections.emptyList());
                } catch (NumberFormatException e) {
                    departments = Collections.emptyList(); // Handle invalid ID input
                }
            } else if (searchBy.equalsIgnoreCase("name")) {
                departments = departmentRepository.findByNameContainingIgnoreCase(searchValue);
            } else {
                departments = departmentRepository.findAll();
            }
        } else {
            departments = departmentRepository.findAll();
        }

        model.addAttribute("departments", departments);
        return "departments";
    }





    




}
