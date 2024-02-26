package com.example.security.securitybasedauth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.securitybasedauth.Entity.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

}
