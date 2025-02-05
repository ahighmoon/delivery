package com.laioffer.delivery.dto;

import com.laioffer.delivery.model.RouteInfo;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Map;

@Data
public class TrackingDto {
    private String status;                // 配送状态
    private Map<String, Double> location;           // 设备实时位置
    private RouteInfo routeInfo;          // 配送路线信息
    private LocalDateTime estimateArrivalTime; // 预计到达时间

    public TrackingDto(){}

    public TrackingDto(String status, Map<String, Double>  location, RouteInfo routeInfo, LocalDateTime estimatedArrivalTime) {
        this.status = status;
        this.location = location;
        this.routeInfo = routeInfo;
        this.estimateArrivalTime = estimatedArrivalTime;
    }
    /**
     * 动态计算预计到达时间
     * @param dispatchedTime 任务分配时间
     * @param estimateTime 估算时间（分钟）
     */
    // 根据dispatchedTime和estimateTime计算预计到达时间
    public void calculateEstimateArrivalTime(LocalDateTime dispatchedTime, int estimateTime) {
        if (dispatchedTime != null && estimateTime > 0) {
            this.estimateArrivalTime = dispatchedTime.plusMinutes(estimateTime);
        } else {
            this.estimateArrivalTime = null;
        }
    }

    @Override
    public String toString() {
        return "TrackingDTO{" +
                "status='" + status +
                ", location='" + location +
                ", routeInfo='" + routeInfo +
                ", estimateArrivalTime=" + estimateArrivalTime +
                '}';
    }
}

