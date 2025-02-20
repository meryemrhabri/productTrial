package com.example.altenKata.service;

import com.example.altenKata.model.User;
import com.example.altenKata.repository.ProductRepository;
import com.example.altenKata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(request);
    }
}
