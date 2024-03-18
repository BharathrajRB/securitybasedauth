package com.example.security.securitybasedauth.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

import java.util.List;

import com.example.security.securitybasedauth.Entity.OrderItem;
import com.example.security.securitybasedauth.Entity.Product;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final EntityManager entityManager;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Object[]> getTotalQuantityPerProductcri() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Join<Product, OrderItem> orderItemJoin = productRoot.join("orderItems");

        criteriaQuery.multiselect(
                productRoot.get("name"),
                criteriaBuilder.sum(orderItemJoin.get("quantity")));
        criteriaQuery.groupBy(productRoot.get("name"));
        criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get("name")));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
