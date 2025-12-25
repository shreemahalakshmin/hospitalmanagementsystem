package com.example.hospitalmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hospitalmanagementsystem.entity.Appointment;
import com.example.hospitalmanagementsystem.entity.AppointmentStatus;
import com.example.hospitalmanagementsystem.entity.AvailabilitySlot;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.User;
import com.example.hospitalmanagementsystem.repository.AppointmentRepository;
import com.example.hospitalmanagementsystem.repository.AvailabilitySlotRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;

    // Book an appointment
    @Transactional
    public Appointment bookAppointment(User patient, AvailabilitySlot slot) {
        if (patient == null || slot == null) {
            throw new RuntimeException("Patient or Slot is missing");
        }

        if (slot.isBooked()) {
            throw new RuntimeException("This slot is already booked");
        }

        slot.setBooked(true); // Mark the slot as booked
        availabilitySlotRepository.save(slot); // Save the updated slot

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(slot.getDoctor());
        appointment.setSlot(slot);
        appointment.setStatus(AppointmentStatus.PENDING); // Set the status to PENDING
        appointment.setBookedTime(LocalDateTime.now());

        return appointmentRepository.save(appointment); // Save the appointment
    }

    // Confirm an appointment by doctor
    public Appointment confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CONFIRMED); // Change status to CONFIRMED
        return appointmentRepository.save(appointment); // Save the updated appointment
    }

    // Get available slots for a doctor
    public List<AvailabilitySlot> getAvailableSlotsForDoctor(Doctor doctor) {
        return availabilitySlotRepository.findByDoctorAndIsBookedFalse(doctor); // Fetch available slots for the doctor
    }

    // Get pending appointments for a doctor
    public List<Appointment> getPendingAppointments(Doctor doctor) {
        return appointmentRepository.findByDoctorAndStatus(doctor, AppointmentStatus.PENDING);
    }
}
