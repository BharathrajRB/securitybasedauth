package com.example.security.securitybasedauth.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoCOntroller {

    @GetMapping("/demo")
    public ResponseEntity<String> getDemo() {
        return ResponseEntity.ok("Demo");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Admin page");
    }

}
