package com.laioffer.delivery.controller;

import com.laioffer.delivery.model.Delivery;
import com.laioffer.delivery.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    // example
    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable String id) {
        // TODO: Implement the actual logic
        Delivery delivery = deliveryService.getDeliveryById(id);
        if (delivery == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delivery);
    }
}
