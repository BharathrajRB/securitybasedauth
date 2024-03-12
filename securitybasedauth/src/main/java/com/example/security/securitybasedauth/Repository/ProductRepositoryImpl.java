package com.example.security.securitybasedauth.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
    public List<Object[]> getTotalQuantityPerProduct() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        Root<Product> productRoot = query.from(Product.class);
        Root<OrderItem> orderItemRoot = query.from(OrderItem.class);
        
        query.multiselect(
                productRoot.get("name").alias("product_name"),
                cb.sum(orderItemRoot.get("quantity")).alias("total_quantity"));
        query.where(cb.equal(orderItemRoot.get("product"), productRoot.get("id")));
        query.groupBy(productRoot.get("name"));
        query.orderBy(cb.asc(productRoot.get("name")));

        return entityManager.createQuery(query).getResultList();
    }
}
