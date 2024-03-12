package com.example.security.securitybasedauth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.security.securitybasedauth.Entity.Orders;
import com.example.security.securitybasedauth.Entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);

    @Query(value = "SELECT order_id, COUNT(DISTINCT product.category_id) AS count " +
            "FROM order_item " +
            "JOIN product ON order_item.product_id = product.id " +
            "GROUP BY order_id " +
            "HAVING count > 1", nativeQuery = true)

    List<Long> findOrderIdsWithProductsInDifferentCategories();

    Optional<Orders> findByIdAndUser(Long id, User user);

}
