package com.example.hospitalmanagementsystem.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotId;
    private LocalDateTime slotTime; 
    private boolean isBooked; 
    
    @ManyToOne
    @JoinColumn(name = "doctor_id")
     @JsonBackReference
    @JsonIgnoreProperties("availabilitySlots")
    private Doctor doctor;

    public AvailabilitySlot() {
    }

    public AvailabilitySlot(LocalDateTime slotTime, boolean isBooked, Doctor doctor) {
        this.slotTime = slotTime;
        this.isBooked = isBooked;
        this.doctor = doctor;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public LocalDateTime getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(LocalDateTime slotTime) {
        this.slotTime = slotTime;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    
}
