package com.example.hospital_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hospital_management.entity.Doctor;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    long countByDepartment_DeptID(int deptID); 
    List<Doctor> findByNameContainingIgnoreCase(String name);

}



