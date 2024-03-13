package com.example.security.securitybasedauth.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Entity.Product;
import com.example.security.securitybasedauth.Service.ProductService;

@RestController
public class GetProducts {

    @Autowired
    private ProductService productservice;

    @GetMapping("/get-products")
    public List<Product> getProduct() {
        return productservice.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productservice.getProductById(productId);
    }

    @GetMapping("/byCategory/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoryName) {
        List<Product> products = productservice.getProductsByCategory(categoryName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/total-quantity")
    public ResponseEntity<List<Object[]>> getTotalQuantityPerProduct() {
        List<Object[]> result = productservice.getTotalQuantityPerProduct();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
/*
 *  SELECT product.name AS product_name, SUM(order_item.quantity) AS total_quantity FROM product JOIN
 *  order_item ON order_item.product_id = product.id GROUP BY product_name ORDER BY product_name
 */
    @GetMapping("/total-cri")
    public ResponseEntity<List<Object[]>> getTotalQuantityPerProductcri() {
        List<Object[]> result = productservice.getTotalQuantityPerProductcri();
        return ResponseEntity.ok(result);
    }
}
