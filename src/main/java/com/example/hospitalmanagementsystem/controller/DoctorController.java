package com.example.hospitalmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.hospitalmanagementsystem.entity.AvailabilitySlot;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Search doctors by specialization or name
    @GetMapping("/search")
    public List<Doctor> searchDoctors(@RequestParam(required = false) String specialization,
                                      @RequestParam(required = false) String name) {
        return doctorService.searchDoctors(specialization, name);
    }

    // Get available slots for a doctor
    @GetMapping("/{doctorId}/available-slots")
    public List<AvailabilitySlot> getAvailableSlots(@PathVariable Long doctorId) {
        Doctor doctor = new Doctor(); // Fetch the doctor by id
        doctor.setDoctorId(doctorId);
        return doctorService.getAvailableSlots(doctor);
    }
}
