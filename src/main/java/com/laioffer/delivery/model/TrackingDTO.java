package com.laioffer.delivery.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class TrackingDTO {
    private String status;                // 配送状态
    private String location;              // 设备实时位置
    private String routeInfo;          // 配送路线信息
    private LocalDateTime estimateArrivalTime; // 预计到达时间

    public TrackingDTO(){}

    public TrackingDTO(String status, String location, String routeInfo, LocalDateTime estimatedArrivalTime) {
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
}

