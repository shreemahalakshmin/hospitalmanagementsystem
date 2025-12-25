package com.example.hospitalmanagementsystem.dto;

import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.User;

public class AppointmentResponseDTO {

    private User patient;
    private Doctor doctor;
    private boolean slotStatus;  

    

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

    public boolean isSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(boolean slotStatus) {
        this.slotStatus = slotStatus;
    }
}
