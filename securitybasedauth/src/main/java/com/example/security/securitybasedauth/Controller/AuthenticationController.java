package com.example.security.securitybasedauth.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Entity.AuthenticationResponse;
import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Service.AuthenticationService;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> regUser(@RequestBody User user) {
        try {
            if (authenticationService.emailExists(user.getEmail())) {
                return ResponseEntity.badRequest()
                    .body(new AuthenticationResponse("User with this email already exists"));
            }
    
            AuthenticationResponse registrationResponse = authenticationService.register(user);
            return ResponseEntity.ok(registrationResponse);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest()
                .body(new AuthenticationResponse("User with this email already exists"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
