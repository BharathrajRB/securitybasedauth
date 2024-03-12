package com.example.security.securitybasedauth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Dto.CartRequestDTO;
import com.example.security.securitybasedauth.Dto.CheckoutRequestDTO;
import com.example.security.securitybasedauth.Service.CartService;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add-cart")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization") String authHeader,
            @RequestBody CartRequestDTO cartRequestDTO) {
        try {
            return cartService.addToCart(cartRequestDTO.getProductId(), cartRequestDTO.getQuantity(), authHeader);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<?> viewCart(@RequestHeader("Authorization") String authHeader) {
        try {
            return cartService.viewCart(authHeader);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestHeader("Authorization") String authHeader,
            @RequestBody CheckoutRequestDTO checkoutRequest) {
        try {
            return cartService.checkout(authHeader, checkoutRequest.getPaymentMethodId(),
                    checkoutRequest.getShippingAddress());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
