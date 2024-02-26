package com.example.security.securitybasedauth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.securitybasedauth.Entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
