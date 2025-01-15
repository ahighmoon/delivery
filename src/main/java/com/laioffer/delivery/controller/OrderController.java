package com.laioffer.delivery.controller;

import com.laioffer.delivery.model.Order;
import com.laioffer.delivery.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // example
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        // TODO: Implement the actual logic
        return ResponseEntity.ok(List.of());
    }

}
