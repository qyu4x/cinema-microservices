package com.neko.orderservice.service.impl;

import com.neko.orderservice.model.request.OrderDetailRequest;
import com.neko.orderservice.model.request.OrderRequest;
import com.neko.orderservice.model.request.OrderSeatRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDetailServiceImplTest {

    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @Test
    void testCreateOrderAndOrderDetails() {

        List<OrderSeatRequest> orderSeatRequests = new ArrayList<>();
        OrderSeatRequest orderSeatRequest = new OrderSeatRequest();
        orderSeatRequest.setSeatCode("A11");
        orderSeatRequest.setStudioName("studio sagiri chan");
        orderSeatRequests.add(orderSeatRequest);

        List<OrderDetailRequest> orderDetailRequests  = new ArrayList<>();
        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setScheduleId("fc5b1366-d0ab-4b55-b6c5-6d9911397781");
        orderDetailRequest.setStudioId("e475e3dc-7c32-49a1-b5ee-cbe2440ee087");
        orderDetailRequest.setQuantity(1);
        orderDetailRequest.setOrderSeatRequests(orderSeatRequests);
        orderDetailRequests.add(orderDetailRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId("ba4c549c-db30-4905-aae5-6a9c8024e188");
        orderRequest.setOrderDetailRequests(orderDetailRequests);
        orderDetailService.createOrderDetail(orderRequest);

    }
}