package com.laioffer.delivery.orderTracking;

import com.laioffer.delivery.dto.TrackingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderTrackingController {

    private final OrderTrackingService orderTrackingService;

    @Autowired
    public OrderTrackingController(OrderTrackingService orderTrackingService) {
        this.orderTrackingService = orderTrackingService;
    }

    // 根据订单ID获取订单追踪信息
    @GetMapping("/tracking")
    public ResponseEntity<TrackingDto> getOrderTracking(@RequestParam("id") String orderId) {
        return Optional.ofNullable(orderTrackingService.getOrderTracking(orderId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
