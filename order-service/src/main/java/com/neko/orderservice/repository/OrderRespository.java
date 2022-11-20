package com.neko.orderservice.repository;

import com.neko.orderservice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;


public interface OrderRespository extends JpaRepository<Order, String> {
    @Modifying
    @Query(
            value = "UPDATE orders SET total_price = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    void updateTotalPriceById(BigDecimal totalPrice, String id);
}
