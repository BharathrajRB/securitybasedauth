package com.example.security.securitybasedauth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // public User getbyEmail(String email) {
    // return userRepository.getbyEmail(email);
    // }
}
