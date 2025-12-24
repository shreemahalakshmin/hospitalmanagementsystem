package com.example.hospitalmanagementsystem.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User patient; 

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor; 

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private AvailabilitySlot slot; 

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private LocalDateTime bookedTime; 

    public Appointment() {
    }

    public Appointment(User patient, Doctor doctor, AvailabilitySlot slot, AppointmentStatus status, LocalDateTime bookedTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.slot = slot;
        this.status = status;
        this.bookedTime = bookedTime;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public AvailabilitySlot getSlot() {
        return slot;
    }

    public void setSlot(AvailabilitySlot slot) {
        this.slot = slot;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(LocalDateTime bookedTime) {
        this.bookedTime = bookedTime;
    }
}
