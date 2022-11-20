package com.neko.orderservice.controller;

import com.neko.orderservice.model.request.OrderRequest;
import com.neko.orderservice.model.response.OrderResponse;
import com.neko.orderservice.model.response.WebResponse;
import com.neko.orderservice.service.OrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/order/")
public class OrderController {

    private final static Logger log = LoggerFactory.getLogger(OrderController.class);

    private OrderDetailService orderDetailService;
    public OrderController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }


    @PostMapping(value = "/ticket")
    @ResponseBody
    public ResponseEntity<WebResponse<OrderResponse>> postRequestOrder(@Valid @RequestBody OrderRequest orderRequest) {
        log.info("#calling controller postRequestRegister");
        try {
            OrderResponse orderResponse = orderDetailService.createOrderDetail(orderRequest);
            log.info("#successfully place an order with order id {}, with user id {} ", orderResponse.getOrderId(), orderRequest.getUserId());
            WebResponse webResponse = new WebResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    orderResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (RuntimeException exception) {
            log.error("failed to place order for user id {}", orderRequest.getUserId());
            WebResponse webResponse = new WebResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
