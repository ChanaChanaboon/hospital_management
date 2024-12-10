package com.example.hospital_management.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

import com.example.hospital_management.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    // Add custom queries if needed
    List<Patient> findByNameContainingIgnoreCase(String name);


}
