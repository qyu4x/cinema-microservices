package com.neko.orderservice.service;

import com.neko.orderservice.model.entity.Order;
import com.neko.orderservice.model.request.OrderRequest;
import com.neko.orderservice.model.response.OrderResponse;

public interface OrderDetailService {

    OrderResponse createOrderDetail(OrderRequest orderRequest);

}
