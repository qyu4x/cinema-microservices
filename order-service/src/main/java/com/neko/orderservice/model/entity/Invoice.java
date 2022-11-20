package com.neko.orderservice.model.entity;

import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Service
@Builder
@Setter
public class Invoice {

    private String orderId;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal totalPrice;

    private LocalDate showDate;

    private LocalTime startTime;

    private String title;

    private String seatCode;

    private String studioName;

    private LocalDateTime orderedAt;
}
