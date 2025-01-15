package com.laioffer.delivery.service;

import com.laioffer.delivery.model.Order;
import com.laioffer.delivery.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // example
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    // example
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}
