package com.laioffer.delivery;


import static org.assertj.core.api.Assertions.assertThat;

import com.laioffer.delivery.controller.OrderController;
import com.laioffer.delivery.repository.OrderRepository;
import com.laioffer.delivery.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
@ActiveProfiles("test")
class DeliveryApplicationTests {

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("delivery_test")
            .withUsername("test_user")
            .withPassword("test_password");

    @BeforeAll
    static void setup() {
        mysql.start();
        System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysql.getUsername());
        System.setProperty("spring.datasource.password", mysql.getPassword());
    }

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void contextLoads() {
        assertThat(orderController).isNotNull();
        assertThat(orderService).isNotNull();
        assertThat(orderRepository).isNotNull();
        System.out.println("Test database running on: " + mysql.getJdbcUrl());
    }

}
