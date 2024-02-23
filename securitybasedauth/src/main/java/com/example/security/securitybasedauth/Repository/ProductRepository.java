package com.example.security.securitybasedauth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.securitybasedauth.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
