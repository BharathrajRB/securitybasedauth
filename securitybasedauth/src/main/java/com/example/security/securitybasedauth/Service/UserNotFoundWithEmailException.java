package com.example.security.securitybasedauth.Service;

public class UserNotFoundWithEmailException extends RuntimeException {
    public UserNotFoundWithEmailException(String message) {
        super(message);
    }

}
