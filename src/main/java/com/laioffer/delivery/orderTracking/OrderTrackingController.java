package com.laioffer.delivery.orderTracking;

import com.laioffer.delivery.model.TrackingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public TrackingDTO getOrderTracking(@RequestParam("id") String orderId) {
        return orderTrackingService.getOrderTracking(orderId);
    }
}
