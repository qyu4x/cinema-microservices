package com.neko.orderservice.service.impl;


import com.neko.orderservice.client.ScheduleServiceExternalClient;
import com.neko.orderservice.client.SeatServiceExternalClient;
import com.neko.orderservice.client.StudioServiceExternalClient;
import com.neko.orderservice.exception.DataNotFoundException;
import com.neko.orderservice.model.entity.Order;
import com.neko.orderservice.model.entity.OrderDetail;
import com.neko.orderservice.model.helper.ScheduleOrderHelper;
import com.neko.orderservice.model.request.OrderDetailRequest;
import com.neko.orderservice.model.request.OrderRequest;
import com.neko.orderservice.model.response.*;
import com.neko.orderservice.repository.OrderDetailRepository;
import com.neko.orderservice.repository.OrderRespository;
import com.neko.orderservice.service.OrderDetailService;
import com.neko.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    private final OrderService orderService;

    private final OrderRespository orderRespository;

    private final ScheduleServiceExternalClient scheduleServiceExternalClient;

    private final StudioServiceExternalClient studioServiceExternalClient;

    private final SeatServiceExternalClient seatServiceExternalClient;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderService orderService, OrderRespository orderRespository, ScheduleServiceExternalClient scheduleServiceExternalClient, StudioServiceExternalClient studioServiceExternalClient, SeatServiceExternalClient seatServiceExternalClient) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderService = orderService;
        this.orderRespository = orderRespository;
        this.scheduleServiceExternalClient = scheduleServiceExternalClient;
        this.studioServiceExternalClient = studioServiceExternalClient;
        this.seatServiceExternalClient = seatServiceExternalClient;
    }


    @Transactional
    @Override
    public OrderResponse createOrderDetail(OrderRequest orderRequest) {
        log.info("carry out the process of making detailed orders with user id {} ", orderRequest.getUserId());
        try {
            List<ScheduleOrderHelper> scheduleOrderDetailResponses = findScheduleOrderRequestByScheduleId(orderRequest.getOrderDetailRequests());
            Order order = orderService.createOrder(orderRequest.getUserId());
            scheduleOrderDetailResponses.stream().forEach(scheduleOrderDetailResponse -> {
                insertIntoTableOrderDetail(scheduleOrderDetailResponse, order.getId());
            });
            BigDecimal totalPriceOrder = getTotalPriceById(order);
            orderRespository.updateTotalPriceById(totalPriceOrder, order.getId());
            List<OrderDetail> dataOrderDetails = orderDetailRepository.findOrderDetailByOrderId(order.getId());
            if (checkSeatAndStudioAvailability(orderRequest) && checkNumberOfSeat(orderRequest)) {


            }
        }catch (DataNotFoundException exception) {
            log.error("error {} ", exception.getMessage());
        }

        return null;
    }

    private List<ScheduleOrderHelper> findScheduleOrderRequestByScheduleId(List<OrderDetailRequest> orderDetailRequests) {
        List<ScheduleOrderHelper> schedulesOrderHelpers = new ArrayList<>();
        orderDetailRequests.stream().forEach(orderDetailRequest -> {
            WebScheduleResponse webScheduleResponse = scheduleServiceExternalClient.findScheduleById(orderDetailRequest.getScheduleId());
            if (webScheduleResponse.getCode() != 200) {
                throw new DataNotFoundException(String.format("schedule with id %s is not found", orderDetailRequest.getScheduleId()));
            }
            ScheduleResponse scheduleResponse = webScheduleResponse.getData();
            ScheduleOrderHelper scheduleOrderHelper = ScheduleOrderHelper.builder()
                    .scheduleId(scheduleResponse.getId())
                    .studioId(orderDetailRequest.getStudioId())
                    .price(scheduleResponse.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
            schedulesOrderHelpers.add(scheduleOrderHelper);

        });
        return schedulesOrderHelpers;
    }

    private void insertIntoTableOrderDetail(ScheduleOrderHelper scheduleOrderHelper, String orderId) {
        OrderDetail orderDetail = OrderDetail.builder()
                .id(UUID.randomUUID().toString())
                .orderId(
                        Order.builder()
                                .id(orderId)
                                .build())
                .price(scheduleOrderHelper.getPrice())
                .studioId(scheduleOrderHelper.getStudioId())
                .quantity(scheduleOrderHelper.getQuantity())
                .createdAt(LocalDateTime.now())
                .build();
        orderDetailRepository.save(orderDetail);
    }

    public BigDecimal getTotalPriceById(Order order) {
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailByOrderId(order.getId());
        AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
        orderDetails.stream()
                .forEach(orderDetail -> {
                    Double price =  orderDetail.getPrice() == null ? 0.0 : orderDetail.getPrice().doubleValue();
                    Double totalPerLine = orderDetail.getQuantity() * price;
                    totalPrice.updateAndGet(base -> base + totalPerLine);
                });

        return new BigDecimal(totalPrice.toString());
    }

    public Boolean checkSeatAndStudioAvailability(OrderRequest orderRequest) {
        orderRequest.getOrderDetailRequests().forEach(orderDetailRequest -> {
            WebStudioResponse studioResponse = studioServiceExternalClient.findStudioById(orderDetailRequest.getStudioId());
            if (studioResponse.getData() == null) {
                throw new DataNotFoundException(String.format("studio with id %s is not found", orderDetailRequest.getScheduleId()));
            }
            orderDetailRequest.getOrderSeatRequests().forEach(orderSeatRequest -> {
                WebSeatResponse seatResponse = seatServiceExternalClient.findIfExist(orderSeatRequest.getSeatCode());
                if (!seatResponse.getData()) {
                    throw new DataNotFoundException(String.format("seat with code %s is not found", orderSeatRequest.getSeatCode()));
                }
            });
        });

        return true;
    }

    public Boolean checkNumberOfSeat(OrderRequest orderRequest) {

        for (int i = 0; i < orderRequest.getOrderDetailRequests().size(); i++) {
            Integer quantity = orderRequest.getOrderDetailRequests().get(i).getQuantity();
            Integer numberOfSeat = orderRequest.getOrderDetailRequests()
                    .get(i).getOrderSeatRequests().size(); // has bug conflic 409

            if (!quantity.equals(numberOfSeat)) {
                log.error("Seat can not be more than quantity");
                throw new DataNotFoundException("Seat can not be more than quantity");
            }
        }

        return true;
    }
}
