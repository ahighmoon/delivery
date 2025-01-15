package com.laioffer.delivery.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "devices")
public class Device {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private String location; // 存储经纬度点信息

    @Column(nullable = false)
    private String station;

    @Column(nullable = false)
    private float capacity = 0.0f;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.IDLE;

    public enum Type {
        ROBOT, DRONE
    }

    public enum Status {
        IDLE, DISPATCHED, IN_TRANSIT
    }
}
