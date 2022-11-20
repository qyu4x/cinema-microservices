package com.neko.orderservice.service.impl;

import com.neko.orderservice.client.UserServiceExternalClient;
import com.neko.orderservice.exception.DataNotFoundException;
import com.neko.orderservice.model.entity.Order;
import com.neko.orderservice.model.response.WebUserResponse;
import com.neko.orderservice.repository.OrderRespository;
import com.neko.orderservice.service.OrderService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserServiceExternalClient userServiceExternalClient;

    private final OrderRespository orderRespository;

    @Autowired
    public OrderServiceImpl(UserServiceExternalClient userServiceExternalClient, OrderRespository orderRespository) {
        this.userServiceExternalClient = userServiceExternalClient;
        this.orderRespository = orderRespository;
    }

    @Override
    public Order createOrder(String userId) {
        WebUserResponse userResponse = userServiceExternalClient.findUserById(userId);
        if (userResponse.getCode().intValue() != 200) {
            throw new DataNotFoundException(String.format("data user with id %s is not found", userId));
        }

        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .userId(userResponse.getData().getId())
                .createdAt(LocalDateTime.now())
                .build();

        orderRespository.save(order);
        return order;
    }

}
