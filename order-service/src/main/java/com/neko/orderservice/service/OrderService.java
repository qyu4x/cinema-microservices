package com.neko.orderservice.service;

import com.neko.orderservice.model.entity.Order;

public interface OrderService {

    Order createOrder(String userId);

}
