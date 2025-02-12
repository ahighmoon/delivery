package com.laioffer.delivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String deliveryId;  // 新增字段，指向 Delivery

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String senderEmail;

    @Column(nullable = false)
    private String senderAddress;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String recipientEmail;

    @Column(nullable = false)
    private String recipientAddress;

    @Column(nullable = false)
    private float packageWeight;

    @Column(nullable = false)
    private String packageDimensions;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ORDERED;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Added a new field (fasted or cheapest), if needed
    @Column
    private String selectedDeliveryType;

//    reserved for payment information (if consider needed)
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private PaymentStatus paymentStatus = PaymentStatus.PENDING; // New Field

    @PreUpdate
    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }


    public enum Status {
        ORDERED, DISPATCHED, IN_TRANSIT, COMPLETED;

        public static Status fromString(String value) {
            return Status.valueOf(value.toUpperCase());
        }
    }
    // reserved for payment information (if consider needed)
//    public enum PaymentStatus {
//        PENDING, PAID
//    }


}

