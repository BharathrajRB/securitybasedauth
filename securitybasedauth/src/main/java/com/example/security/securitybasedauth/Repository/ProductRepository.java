package com.example.security.securitybasedauth.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import com.example.security.securitybasedauth.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    List<Product> findByCategoryid_Name(String categoryName);

    Page<Product> findAll(Pageable pageable);
}
