package com.example.security.securitybasedauth.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.example.security.securitybasedauth.Controller.OrderNotFoundException;
import com.example.security.securitybasedauth.Dto.OrderDTO;
import com.example.security.securitybasedauth.Dto.OrderDetailsDTO;
import com.example.security.securitybasedauth.Dto.OrderItemDTO;
import com.example.security.securitybasedauth.Entity.OrderItem;
import com.example.security.securitybasedauth.Entity.Orders;
import com.example.security.securitybasedauth.Entity.User;
import com.example.security.securitybasedauth.Repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDTO> getOrderHistory(User user) {

        List<Orders> orders = orderRepository.findByUser(user);
        List<OrderDTO> orderHistoryList = new ArrayList<>();
        for (Orders order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setPaymentMethod(order.getPaymentMethod().getName());
            int totalCount = order.getOrderItems().stream().mapToInt(OrderItem::getQuantity).sum();
            orderDTO.setTotalCount(totalCount);
            orderDTO.setTotalAmount(order.getTotalPrice());
            orderHistoryList.add(orderDTO);

        }
        return orderHistoryList;

    }

    public OrderDetailsDTO getOrderById(Long orderId, User user) {
        Optional<Orders> optionalOrder = orderRepository.findByIdAndUser(orderId, user);

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();
            OrderDetailsDTO orderDetailsDTO = mapToOrderDetailsDTO(order);
            List<OrderItemDTO> orderItemDTOList = order.getOrderItems().stream()
                    .map(this::mapToOrderItemDTO)
                    .collect(Collectors.toList());
            orderDetailsDTO.setOrderItems(orderItemDTOList);

            return orderDetailsDTO;
        } else {
            throw new OrderNotFoundException("Order not found");
        }
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setProductName(orderItem.getProduct().getName());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        return orderItemDTO;
    }

    private OrderDetailsDTO mapToOrderDetailsDTO(Orders order) {
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setOrderId(order.getId());
        orderDetailsDTO.setOrderDate(order.getOrderDate());
        orderDetailsDTO.setPaymentMethod(order.getPaymentMethod().getName());
        orderDetailsDTO.setShippingAddress(order.getShippingAddress());
        orderDetailsDTO.setTotalAmount(order.getTotalPrice());
        return orderDetailsDTO;
    }

}