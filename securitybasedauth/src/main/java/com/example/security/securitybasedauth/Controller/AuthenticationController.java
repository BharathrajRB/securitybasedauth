package com.example.security.securitybasedauth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Entity.AuthenticationResponse;
import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Service.AuthenticationService;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> regUser(@RequestBody User user) {
        AuthenticationResponse response = authenticationService.register(user);
        if (response.getToken() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody User request) {
        AuthenticationResponse response = authenticationService.authenticate(request);

        if (response.getToken() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}