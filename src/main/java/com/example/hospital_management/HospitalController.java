package com.example.hospital_management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class HospitalController {

    @GetMapping("/") // Map the home page
    public String home() {
        return "home"; // This points to the Thymeleaf template `home.html`
    }

    
}
