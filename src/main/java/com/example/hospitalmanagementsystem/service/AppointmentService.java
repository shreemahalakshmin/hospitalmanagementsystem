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

    @Transactional
    public Appointment bookAppointment(User patient, AvailabilitySlot slot) {
        if (patient == null || slot == null) {
            throw new RuntimeException("Patient or Slot is missing");
        }

        if (!slot.isBooked()) {
            slot.setBooked(true); 
            availabilitySlotRepository.save(slot);  // Save the updated slot

            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(slot.getDoctor());
            appointment.setSlot(slot);
            appointment.setStatus(AppointmentStatus.PENDING);
            appointment.setBookedTime(LocalDateTime.now());

            return appointmentRepository.save(appointment);  // Save the new appointment
        } else {
            throw new RuntimeException("This slot is already booked");
        }
    }

    public Appointment confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getPendingAppointments(Doctor doctor) {
        return appointmentRepository.findByDoctorAndStatus(doctor, AppointmentStatus.PENDING);
    }
}
