package com.example.security.securitybasedauth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.securitybasedauth.Entity.CartItem;
import com.example.security.securitybasedauth.Entity.Product;
import com.example.security.securitybasedauth.Entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
