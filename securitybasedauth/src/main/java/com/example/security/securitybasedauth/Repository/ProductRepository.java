package com.example.security.securitybasedauth.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import com.example.security.securitybasedauth.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    List<Product> findByCategoryid_Name(String categoryName);

    Page<Product> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT product.name AS product_name, SUM(order_item.quantity) AS total_quantity "
            +
            "FROM product " +
            "JOIN order_item ON order_item.product_id = product.id " +
            "GROUP BY product_name " +
            "ORDER BY product_name")
    List<Object[]> getTotalQuantityPerProduct();
}
