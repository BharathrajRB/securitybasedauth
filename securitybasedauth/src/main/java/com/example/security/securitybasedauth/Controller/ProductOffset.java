package com.example.security.securitybasedauth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Entity.Product;
import com.example.security.securitybasedauth.Service.ProductService;

@RestController
public class ProductOffset {

    @Autowired
    private ProductService productService;

    @GetMapping("/products-offset")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "01") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        Page<Product> products = productService.getAllProducts(page, size, sortBy);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products-pagination")
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return productService.getPaginatedProducts(page, size);
    }

}
