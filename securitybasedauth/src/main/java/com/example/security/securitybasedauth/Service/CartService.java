package com.example.security.securitybasedauth.Service;

import java.math.BigDecimal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.security.securitybasedauth.Entity.CartItem;
import com.example.security.securitybasedauth.Entity.Product;
import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Repository.CartItemRepository;
import com.example.security.securitybasedauth.Repository.UserRepository;

@Service
public class CartService {
    @Autowired
    private ProductService productService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    // public void addToCart(Long productId, int quantity, String authHeader) {
    // try {
    // String token = authHeader.replace("Bearer ", "");
    // String email = jwtService.extractUsername(token);

    // Optional<User> optionalUser = userRepository.findByEmail(email);

    // if (optionalUser.isPresent()) {
    // User user = optionalUser.get();

    // Product product = productService.getProductById(productId);

    // if (product != null) {
    // if (quantity > product.getAvailableStock()) {
    // throw new RuntimeException("Requested quantity exceeds available stock");
    // }

    // if (quantity != 0) {
    // Optional<CartItem> existingCartItem =
    // cartItemRepository.findByUserAndProduct(user, product);

    // if (existingCartItem.isPresent() ) {
    // CartItem cartItem = existingCartItem.get();
    // cartItem.setQuantity(cartItem.getQuantity() + quantity);
    // cartItemRepository.save(cartItem);
    // } else {
    // CartItem cartItem = new CartItem();
    // cartItem.setProduct(product);
    // cartItem.setQuantity(quantity);
    // cartItem.setUser(user);
    // cartItemRepository.save(cartItem);
    // }
    // } else {
    // throw new RuntimeException("Product quantity less than 0");
    // }
    // } else {
    // throw new RuntimeException("Product with id not found");
    // }
    // } else {
    // throw new RuntimeException("User with email not found");
    // }
    // } catch (Exception e) {
    // throw new RuntimeException("Failed to add product to the cart", e);
    // }
    // }

    // // working
    // public void addToCart(Long productId, int quantity, String authHeader) {
    // try {
    // String token = authHeader.replace("Bearer ", "");
    // String email = jwtService.extractUsername(token);

    // Optional<User> optionalUser = userRepository.findByEmail(email);

    // if (optionalUser.isPresent()) {
    // User user = optionalUser.get();
    // Product product = productService.getProductById(productId);

    // if (product != null) {
    // if (quantity != 0) {
    // Optional<CartItem> existingCartItem =
    // cartItemRepository.findByUserAndProduct(user, product);

    // if (existingCartItem.isPresent()) {
    // CartItem cartItem = existingCartItem.get();

    // if (quantity > 0) {
    // // Increase quantit
    // cartItem.setQuantity(cartItem.getQuantity() + quantity);
    // } else if (quantity < 0 && cartItem.getQuantity() >= -quantity) {
    // // Decrease quantity if existing quantity is sufficient
    // cartItem.setQuantity(cartItem.getQuantity() + quantity);

    // } else {
    // throw new RuntimeException("Invalid quantity for reducing");
    // }

    // cartItemRepository.save(cartItem);
    // } else {
    // // Add new item
    // CartItem cartItem = new CartItem();
    // cartItem.setProduct(product);
    // cartItem.setQuantity(quantity);
    // cartItem.setUser(user);
    // cartItemRepository.save(cartItem);
    // }
    // } else {
    // throw new RuntimeException("Product quantity should not be 0");
    // }
    // } else {
    // throw new RuntimeException("Product with id not found");
    // }
    // } else {
    // throw new RuntimeException("User with email not found");
    // }
    // } catch (Exception e) {
    // throw new RuntimeException("Failed to add product to the cart", e);
    // }
    // } good

    public ResponseEntity<String> addToCart(Long productId, int quantity, String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtService.extractUsername(token);

            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Product product = productService.getProductById(productId);

                if (product != null) {
                    if (quantity != 0) {
                        Optional<CartItem> existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

                        if (existingCartItem.isPresent()) {
                            CartItem cartItem = existingCartItem.get();

                            if (quantity > 0) {
                                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                                cartItemRepository.save(cartItem);
                                return new ResponseEntity<>("Added to the existing cart", HttpStatus.OK);
                            } else if (quantity < 0 && cartItem.getQuantity() >= -quantity) {
                                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                                if (cartItem.getQuantity() <= 0) {
                                    cartItemRepository.delete(cartItem);
                                    return new ResponseEntity<>(
                                            "removed the item from the cart", HttpStatus.OK);
                                }
                                cartItemRepository.save(cartItem);
                                return new ResponseEntity<>("Decreased the quantity of the cart", HttpStatus.OK);
                            } else {
                                throw new RuntimeException("Invalid quantity for reducing");
                            }
                        } else {
                            CartItem cartItem = new CartItem();
                            cartItem.setProduct(product);
                            cartItem.setQuantity(quantity);
                            cartItem.setUser(user);
                            cartItemRepository.save(cartItem);
                            return new ResponseEntity<>("Added to the cart", HttpStatus.OK);
                        }
                    } else {
                        throw new RuntimeException("Product quantity should not be 0");
                    }
                } else {
                    throw new RuntimeException("Product with id not found");
                }
            } else {
                throw new RuntimeException("User with email not found");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> viewCart(String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtService.extractUsername(token);
            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<CartItem> cart = user.getCartItem();
                if (cart.isEmpty()) {
                    return new ResponseEntity<>("cart is empty", HttpStatus.BAD_REQUEST);

                } else {
                    BigDecimal totalPrice = cart.stream()
                            .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    Map<String, Object> response = new HashMap<>();
                    response.put("totalPrice", totalPrice);
                    response.put("cartItems", cart);

                    return new ResponseEntity<>(response, HttpStatus.OK);

                }

            } else {
                return new ResponseEntity<>("User with email not found", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}