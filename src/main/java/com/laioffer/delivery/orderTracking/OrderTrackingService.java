package com.laioffer.delivery.orderTracking;

import com.laioffer.delivery.dto.TrackingDto;
import com.laioffer.delivery.model.*;
import com.laioffer.delivery.repository.DeliveryRepository;
import com.laioffer.delivery.repository.DeviceRepository;
import com.laioffer.delivery.repository.OrderRepository; // 需要引入 OrderRepository

import com.laioffer.delivery.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderTrackingService {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public OrderTrackingService(OrderRepository orderRepository,
                                DeliveryRepository deliveryRepository,
                                DeviceRepository deviceRepository) {
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
        this.deviceRepository = deviceRepository;
    }

    // 获取订单追踪信息
    public TrackingDto getOrderTracking(String orderId) {
        // 查询订单信息
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 使用 order 表中的 delivery_id 查找配送信息
        String deliveryId = order.getDeliveryId();
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        // 创建并填充 TrackingDto
        TrackingDto trackingDto = new TrackingDto();

        // 设置配送状态
        trackingDto.setStatus(delivery.getOrderStatus());

        // 查询设备位置
        Device device = deviceRepository.findById(delivery.getDeviceId()).orElse(null);
        if (device != null && device.getLocation() != null) {
            try {
                Map<String, Double> locationMap = JsonUtils.convertJsonToMapDouble(device.getLocation());
                trackingDto.setLocation(locationMap);
            } catch (Exception e) {
                trackingDto.setLocation(Map.of("error", -1.0)); // 用 -1.0 表示错误
            }
        } else {
            trackingDto.setLocation(Map.of("error", -1.0)); // 设备不存在或无位置数据
        }

        // 查询配送路线
        String routeInfoJson = delivery.getRouteInfo();
        RouteInfo routeInfo = RouteInfo.convertJsonToRouteInfo(routeInfoJson);
        trackingDto.setRouteInfo(routeInfo);

        // 计算预计到达时间
        trackingDto.calculateEstimateArrivalTime(delivery.getDispatchedTime(), delivery.getEstimateTime());

        return trackingDto;
    }

}
