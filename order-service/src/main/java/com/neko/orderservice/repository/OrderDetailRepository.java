package com.neko.orderservice.repository;

import com.neko.orderservice.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findOrderDetailByOrderId(String id);

}
