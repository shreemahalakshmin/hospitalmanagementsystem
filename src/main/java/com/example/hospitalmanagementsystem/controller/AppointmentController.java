package com.example.hospitalmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hospitalmanagementsystem.entity.Appointment;
import com.example.hospitalmanagementsystem.entity.AvailabilitySlot;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.User;
import com.example.hospitalmanagementsystem.repository.AvailabilitySlotRepository;
import com.example.hospitalmanagementsystem.repository.DoctorRepository;
import com.example.hospitalmanagementsystem.repository.UserRepository;
import com.example.hospitalmanagementsystem.service.AppointmentService;
import com.example.hospitalmanagementsystem.dto.AppointmentRequest;  // Import the DTO

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;

    // Book an appointment
   @PostMapping("/book")
public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request) {
    try {
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        AvailabilitySlot slot = availabilitySlotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        Appointment appointment = appointmentService.bookAppointment(patient, slot);

        return ResponseEntity.ok(appointment); // Return 200 OK with the appointment data
    } catch (RuntimeException e) {
        // Log error details
        System.out.println("Error while booking appointment: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());  // Return 500 with error message
    }
}



    // Doctor confirms an appointment
    @PutMapping("/confirm/{appointmentId}")
    public Appointment confirmAppointment(@PathVariable Long appointmentId) {
        return appointmentService.confirmAppointment(appointmentId);
    }

    // Get pending appointments for a doctor
    @GetMapping("/pending/{doctorId}")
    public List<Appointment> getPendingAppointments(@PathVariable Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        return appointmentService.getPendingAppointments(doctor);
    }
}
