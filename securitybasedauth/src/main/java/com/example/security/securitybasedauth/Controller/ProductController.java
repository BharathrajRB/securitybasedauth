package com.example.security.securitybasedauth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Entity.Product;
import com.example.security.securitybasedauth.Repository.ProductRepository;
import com.example.security.securitybasedauth.Service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    // @PostMapping("/products")
    // public ResponseEntity<String> createProduct(@RequestBody Product product) {
    // try {

    // return productService.createProduct(product).ok("success");
    // } catch (UnAuthorizeException e) {
    // return new ResponseEntity<String>("UnAuthorized", HttpStatus.UNAUTHORIZED);
    // }

    // }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            return productService.createProduct(product);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        try {

            return productService.updateProduct(productId, product);
        } catch (Exception e) {
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            return productService.deleteProduct(productId);
        } catch (Exception e) {

            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

}
