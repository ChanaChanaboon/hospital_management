package com.example.hospital_management.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.example.hospital_management.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    // Add custom queries if needed
    @Query("SELECT a FROM Appointment a WHERE a.patient.name LIKE %:name%")
    List<Appointment> findByPatientNameContainingIgnoreCase(@Param("name") String name);
    @Query("SELECT a FROM Appointment a WHERE a.patient IS NOT NULL")
    List<Appointment> findAllWithValidPatients();

    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.doctor")
    List<Appointment> findAllAppointments();


}
