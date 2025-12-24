package com.example.hospitalmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospitalmanagementsystem.entity.AvailabilitySlot;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.repository.AvailabilitySlotRepository;
import com.example.hospitalmanagementsystem.repository.DoctorRepository;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;

    // Find doctors by specialization or name
    public List<Doctor> searchDoctors(String specialization, String name) {
        if (specialization != null && !specialization.isEmpty()) {
            return doctorRepository.findBySpecialization(specialization);
        } else if (name != null && !name.isEmpty()) {
            return doctorRepository.findByNameContaining(name);
        } else {
            return doctorRepository.findAll();
        }
    }

    // Get available slots for a doctor
    public List<AvailabilitySlot> getAvailableSlots(Doctor doctor) {
        return availabilitySlotRepository.findByDoctorAndIsBookedFalse(doctor);
    }
}
