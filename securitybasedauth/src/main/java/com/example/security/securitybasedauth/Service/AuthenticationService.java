package com.example.security.securitybasedauth.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.securitybasedauth.Entity.AuthenticationResponse;
import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public AuthenticationResponse register(User req) {
        try {
            User user = new User();
            if (userRepository.findByEmail(req.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User with this email already exists");
            }

            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setEmail(req.getEmail());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setRoleid(req.getRoleid());
            userRepository.save(user);
            String token = jwtService.generateToken(user);

            return new AuthenticationResponse(token, "User registration was successful");

        } catch (UserAlreadyExistsException e) {
            return new AuthenticationResponse(null, "User already exists");
        }
    }

    public AuthenticationResponse authenticate(User request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = userRepository.findByEmail(request.getUsername()).orElseThrow();
            String token = jwtService.generateToken(user);

            return new AuthenticationResponse(token, "User authenticated successfully");

        } catch (BadCredentialsException e) {
            
            return new AuthenticationResponse(null, "Invalid email or password");
        }
    }

}
