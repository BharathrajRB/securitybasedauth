package com.example.security.securitybasedauth.Entity;

public class AuthenticationResponse {

    private String token;
    private String message;

    // Constructors, getters, and setters...

    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
