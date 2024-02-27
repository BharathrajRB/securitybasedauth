package com.example.security.securitybasedauth.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.example.security.securitybasedauth.Dto.OrderDTO;
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

}