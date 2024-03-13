package com.example.security.securitybasedauth.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.security.securitybasedauth.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT category.name, SUM(order_item.price * order_item.quantity) AS total " +
            "FROM order_item " +
            "JOIN product ON product.id = order_item.product_id " +
            "JOIN category ON category.id = product.category_id " +
            "GROUP BY category.name", nativeQuery = true)
    List<Object[]> getCategoryTotalPrices();
}
