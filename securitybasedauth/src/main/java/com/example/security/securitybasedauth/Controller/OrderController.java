package com.example.security.securitybasedauth.Controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Dto.OrderDTO;
import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Repository.UserRepository;
import com.example.security.securitybasedauth.Service.JwtService;
import com.example.security.securitybasedauth.Service.OrderService;

@RestController
public class OrderController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping("/order-history")
    public ResponseEntity<?> getOrderHistory(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtService.extractUsername(token);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<OrderDTO> orderDTOList = orderService.getOrderHistory(user);
                return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
            }
            return new ResponseEntity<>("User not found ", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
