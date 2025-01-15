package com.laioffer.delivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
