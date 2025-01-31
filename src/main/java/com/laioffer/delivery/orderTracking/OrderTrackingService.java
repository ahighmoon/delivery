package com.laioffer.delivery.orderTracking;

import com.laioffer.delivery.model.Delivery;
import com.laioffer.delivery.model.Device;
import com.laioffer.delivery.model.TrackingDTO;
import com.laioffer.delivery.repository.DeliveryRepository;
import com.laioffer.delivery.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderTrackingService {

    private final DeliveryRepository deliveryRepository;
    private final DeviceRepository deviceRepository;  

    @Autowired
    public OrderTrackingService(DeliveryRepository deliveryRepository, DeviceRepository deviceRepository) {
        this.deliveryRepository = deliveryRepository;
        this.deviceRepository = deviceRepository;
    }

    // 获取订单追踪信息
    public TrackingDTO getOrderTracking(String orderId) {
        // 查询订单对应的配送任务
        Delivery delivery = deliveryRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        // 创建并填充TrackingDTO
        TrackingDTO trackingDTO = new TrackingDTO();

        // 设置配送状态
        trackingDTO.setStatus(delivery.getOrderStatus());

        // 查询设备位置
        Device device = deviceRepository.findById(delivery.getDeviceId())
                .orElse(null);
        if (device != null) {
            trackingDTO.setLocation(device.getLocation());
        } else {
            trackingDTO.setLocation("Location not available");
        }

        // 设置配送路线信息
        String routeInfo = delivery.getRouteInfo();
        trackingDTO.setRouteInfo(routeInfo);

        // 计算预计到达时间
        trackingDTO.calculateEstimateArrivalTime(delivery.getDispatchedTime(), delivery.getEstimateTime());


        return trackingDTO;
    }
}
