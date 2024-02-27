package com.example.security.securitybasedauth.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.security.securitybasedauth.Controller.UnAuthorizeException;
import com.example.security.securitybasedauth.Entity.Product;
import com.example.security.securitybasedauth.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> createProduct(Product product) {
        try {

            Product newProduct = product;
            if (newProduct.getAvailableStock() > 0) {
                if (newProduct.getPrice().signum() < 0) {
                    return new ResponseEntity<>("Product price cannot be a negative number",
                            HttpStatus.BAD_REQUEST);
                }
                productRepository.save(newProduct);
                return new ResponseEntity<>("Product created successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product available stock is less than 0",
                        HttpStatus.BAD_REQUEST);

            }

        } catch (UnAuthorizeException e) {
            return new ResponseEntity<>("U are unauthorized person:", HttpStatus.FORBIDDEN);
        }

        catch (Exception e) {
            return new ResponseEntity<>("unauthorize",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateProduct(Long productId, Product product) {
        Optional<Product> existingProdcut = productRepository.findById(productId);
        if (existingProdcut.isPresent()) {
            Product updateProduct = existingProdcut.get();
            updateProduct.setName(product.getName());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setPrice(product.getPrice());
            updateProduct.setAvailableStock(product.getAvailableStock());
            updateProduct.setActive(product.isActive());
            productRepository.save(updateProduct);
            return new ResponseEntity<>("successfully updated ", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("product is not found with id ", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteProduct(Long productId) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            productRepository.deleteById(productId);
            return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failed to find product", HttpStatus.NOT_FOUND);
        }

    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    // public Product getProductById(Long productId) {
    // Optional<Product> product = productRepository.findById(productId);
    // if (product.isPresent()) {
    // Product productFound= product.get();
    // return productFound;
    // }
    // }

    public Product getProductById(Long id) {
        try {
            return productRepository.findById(id).orElseThrow(() -> new NotFoundException());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategoryid_Name(categoryName);
    }
}