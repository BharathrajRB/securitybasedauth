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

}
