package com.example.hospitalmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hospitalmanagementsystem.entity.Appointment;
import com.example.hospitalmanagementsystem.entity.User;
import com.example.hospitalmanagementsystem.entity.AvailabilitySlot;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.repository.AvailabilitySlotRepository;
import com.example.hospitalmanagementsystem.repository.DoctorRepository;
import com.example.hospitalmanagementsystem.repository.UserRepository;
import com.example.hospitalmanagementsystem.service.AppointmentService;
import com.example.hospitalmanagementsystem.dto.AppointmentRequest;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;

    // Book an appointment
    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request) {
        try {
            User patient = userRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            AvailabilitySlot slot = availabilitySlotRepository.findById(request.getSlotId())
                    .orElseThrow(() -> new RuntimeException("Slot not found"));

            // Call the service to book the appointment
            Appointment appointment = appointmentService.bookAppointment(patient, slot);

            return ResponseEntity.status(HttpStatus.CREATED).body(appointment);  // Return 201 Created with the appointment data
        } catch (RuntimeException e) {
            // Log error details
            System.out.println("Error while booking appointment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());  // Return 400 Bad Request
        }
    }

    // Doctor confirms an appointment
    @PutMapping("/confirm/{appointmentId}")
    public ResponseEntity<?> confirmAppointment(@PathVariable Long appointmentId) {
        try {
            Appointment appointment = appointmentService.confirmAppointment(appointmentId);
            return ResponseEntity.ok(appointment);  // Return 200 OK with the updated appointment data
        } catch (RuntimeException e) {
            // Log error details
            System.out.println("Error while confirming appointment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());  // Return 400 Bad Request
        }
    }

    // Get pending appointments for a doctor
    @GetMapping("/pending/{doctorId}")
    public ResponseEntity<?> getPendingAppointments(@PathVariable Long doctorId) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            
            List<Appointment> pendingAppointments = appointmentService.getPendingAppointments(doctor);
            
            if (pendingAppointments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No pending appointments found.");
            }

            return ResponseEntity.ok(pendingAppointments);  // Return 200 OK with the list of pending appointments
        } catch (RuntimeException e) {
            // Log error details
            System.out.println("Error while fetching pending appointments: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());  // Return 400 Bad Request
        }
    }
}
