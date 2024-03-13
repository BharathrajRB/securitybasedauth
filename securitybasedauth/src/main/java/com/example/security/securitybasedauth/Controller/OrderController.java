package com.example.security.securitybasedauth.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Dto.OrderDTO;
import com.example.security.securitybasedauth.Dto.OrderDetailsDTO;
import com.example.security.securitybasedauth.Entity.Orders;
import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Repository.OrderRepository;
import com.example.security.securitybasedauth.Repository.UserRepository;
import com.example.security.securitybasedauth.Service.JwtService;
import com.example.security.securitybasedauth.Service.OrderService;

@RestController
@RequestMapping("/order-history")
public class OrderController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getOrderHistory(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtService.extractUsername(token);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<OrderDTO> orderDTOList = orderService.getOrderHistory(user);
                return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
            }
            return new ResponseEntity<>("User not found ", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsDTO> getOrderById(@PathVariable Long orderId,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtService.extractUsername(token);

            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                OrderDetailsDTO orderDetailsDTO = orderService.getOrderById(orderId, user);
                return new ResponseEntity<>(orderDetailsDTO, HttpStatus.OK);
            } else {
                throw new UserNotFoundException("User not found");
            }

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/different-categories")
    public ResponseEntity<List<Long>> getOrderIdsWithProductsInDifferentCategories() {
        List<Long> orderIds = orderRepository.findOrderIdsWithProductsInDifferentCategories();
        return ResponseEntity.ok(orderIds);
    }

    @GetMapping("/different-criteria")
    public List<Long> findOrdersWithDifferentCategories() {
        return orderService.findOrderIdsWithProductsInDifferentCategoriescri();
    }

    @GetMapping("/category-prices")
    public List<Object[]> getCategoryTotalPrices() {
        return orderService.getCategoryTotalPrices();
    }
    @GetMapping("/category-total-criteria")
    public List<Object[]> getCategoryTotalPricescri() {
        return orderService.getCategoryTotalPrices();
    }

}
