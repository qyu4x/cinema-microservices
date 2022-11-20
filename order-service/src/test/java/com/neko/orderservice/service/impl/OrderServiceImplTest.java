package com.neko.orderservice.service.impl;

import com.neko.orderservice.model.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    void testCreateOrder() {

        Order orderResponse = orderService.createOrder("e3931602-b12c-4d6d-9a6a-fb8a9450578e");
        Assertions.assertEquals(orderResponse.getUserId(), "e3931602-b12c-4d6d-9a6a-fb8a9450578e");
    }
}