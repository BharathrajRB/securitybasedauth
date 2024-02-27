package com.example.security.securitybasedauth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.securitybasedauth.Entity.Orders;
import com.example.security.securitybasedauth.Entity.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);

}
