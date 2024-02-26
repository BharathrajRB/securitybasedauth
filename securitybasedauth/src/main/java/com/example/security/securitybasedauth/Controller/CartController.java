package com.example.security.securitybasedauth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.security.securitybasedauth.Service.CartService;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add-cart/{productId}")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization") String authHeader,
            @PathVariable Long productId,
            @RequestParam int quantity) {
        try {
            return cartService.addToCart(productId, quantity, authHeader);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewCart(@RequestHeader("Authorization") String authHeader) {
        try {
            return cartService.viewCart(authHeader);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestHeader("Authorization") String authHeader,
            @RequestParam("paymentMethodId") int paymentMethodId,
            @RequestParam("shippingAddress") String shippingAddress) {
        try {
            return cartService.checkout(authHeader, paymentMethodId, shippingAddress);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
