package com.example.hospitalmanagementsystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospitalmanagementsystem.entity.AvailabilitySlot;
import com.example.hospitalmanagementsystem.entity.Doctor;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {
    List<AvailabilitySlot> findByDoctorAndIsBookedFalse(Doctor doctor); // Find available slots for a doctor
    List<AvailabilitySlot> findBySlotTimeBetween(LocalDateTime start, LocalDateTime end); // Search by time range
}