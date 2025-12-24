package com.example.hospitalmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospitalmanagementsystem.entity.User;
import com.example.hospitalmanagementsystem.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Login method without password encoding
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && password.equals(user.getPassword())) { // Comparing plain text password
            return user;  // User authenticated
        }
        return null;  // Authentication failed
    }
}
