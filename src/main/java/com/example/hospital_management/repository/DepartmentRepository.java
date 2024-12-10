package com.example.hospital_management.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hospital_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // Add custom queries if needed
    List<Department> findByNameContainingIgnoreCase(String name);

}
