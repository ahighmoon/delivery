package com.laioffer.delivery.service;

import com.laioffer.delivery.model.Order;
import com.laioffer.delivery.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrderDetails(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersBySenderName(String senderName) {
        return orderRepository.findBySenderName(senderName);
    }

    public List<Order> getOrdersByRecipientName(String recipientName) {
        return orderRepository.findByRecipientName(recipientName);
    }


    // example
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setStatus(Order.Status.ORDERED);
        return orderRepository.save(order);
    }

    public Optional<Order> selectDeliveryType(String orderId, String deliveryType) {
        return orderRepository.findById(orderId).map(order -> {
            order.setSelectedDeliveryType(deliveryType);
            return orderRepository.save(order);
        });
    }
    // payment status if needed
//    public Optional<Order> markOrderAsPaid(String orderId) {
//        return orderRepository.findById(orderId).map(order -> {
//            order.setPaymentStatus(Order.PaymentStatus.PAID);
//            order.setStatus(Order.Status.DISPATCHED);
//            return orderRepository.save(order);
//        });
//    }
}
