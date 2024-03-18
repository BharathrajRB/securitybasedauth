package com.example.security.securitybasedauth.Repository;

import org.springframework.stereotype.Repository;

import com.example.security.securitybasedauth.Entity.Category;
import com.example.security.securitybasedauth.Entity.OrderItem;
import com.example.security.securitybasedauth.Entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements OrderRepositoryCustom {

    private final EntityManager entityManager;

    /*
     * 
     * "SELECT order_id, COUNT(DISTINCT product.category_id) AS count " +
     * "FROM order_item " +
     * "JOIN product ON order_item.product_id = product.id " +
     * "GROUP BY order_id " +
     * "HAVING count > 1
     * 
     * //
     */

    @Override
    public List<Long> findOrderIdsWithProductsInDifferentCategoriescri() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<OrderItem> orderItemRoot = query.from(OrderItem.class);
        Join<OrderItem, Product> productJoin = orderItemRoot.join("product", JoinType.INNER);
        Join<Product, Category> categoryJoin = productJoin.join("categoryid", JoinType.INNER); // Change this line

        query.select(orderItemRoot.get("order").get("id"));
        query.groupBy(orderItemRoot.get("order").get("id"));
        query.having(cb.gt(cb.countDistinct(categoryJoin.get("id")), 1)); // Update the path here as well

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> getCategoryTotalPricescri() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<OrderItem> orderItemRoot = query.from(OrderItem.class);
        Join<OrderItem, Product> productJoin = orderItemRoot.join("product");
        Join<Product, Category> categoryJoin = productJoin.join("categoryid");

        query.multiselect(categoryJoin.get("name"),
                cb.sum(cb.prod(orderItemRoot.get("price"), orderItemRoot.get("quantity"))));

        query.groupBy(categoryJoin.get("name"));

        return entityManager.createQuery(query).getResultList();
    }
}