package com.laioffer.delivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type deliveryType;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false, columnDefinition = "JSON")
    private String routeInfo;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int estimateTime;

    private int actualTime;
    private LocalDateTime dispatchedTime;
    private LocalDateTime transitStartTime;
    private LocalDateTime transitEndTime;
    private LocalDateTime finishTime;

    public enum Type {
        ROBOT, DRONE
    }

}
