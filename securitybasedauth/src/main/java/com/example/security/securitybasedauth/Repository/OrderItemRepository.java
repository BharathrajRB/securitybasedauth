package com.example.security.securitybasedauth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.securitybasedauth.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
