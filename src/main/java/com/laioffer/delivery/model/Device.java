package com.laioffer.delivery.model;

import jakarta.persistence.*;

import java.awt.*;
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

    @Column(nullable = false, columnDefinition = "JSON")
    private String location;  // 将 location 映射为 Point 类型

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
