package com.laioffer.delivery;

import com.laioffer.delivery.controller.OrderController;
import com.laioffer.delivery.repository.OrderRepository;
import com.laioffer.delivery.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DeliveryApplicationTests {

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
    }

}
