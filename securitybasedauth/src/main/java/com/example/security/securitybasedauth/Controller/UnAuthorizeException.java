package com.example.security.securitybasedauth.Controller;

public class UnAuthorizeException extends RuntimeException {

    public UnAuthorizeException(String message) {
        super(message);
    }
}