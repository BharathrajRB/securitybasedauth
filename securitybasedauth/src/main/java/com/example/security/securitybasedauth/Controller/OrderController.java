package com.example.security.securitybasedauth.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.securitybasedauth.Dto.OrderDTO;
import com.example.security.securitybasedauth.Dto.OrderDetailsDTO;
import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Repository.UserRepository;
import com.example.security.securitybasedauth.Service.JwtService;
import com.example.security.securitybasedauth.Service.OrderService;

@RestController
@RequestMapping("/order-history")
public class OrderController {
    @Autowired
    private JwtService jwtService;

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

}
/*
 public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        // Check if the user has the "ROLE_ADMIN" authority
        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Set the appropriate response message
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if (isAdmin) {
            response.getWriter().write("Access denied. Admins only!");
        } else {
            response.getWriter().write("Access denied. You don't have permission to access this resource.");
        }
    }
}
 */