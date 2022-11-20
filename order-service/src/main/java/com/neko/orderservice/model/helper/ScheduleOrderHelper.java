package com.neko.orderservice.model.helper;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleOrderHelper {

    private String scheduleId;

    private String studioId;

    private BigDecimal price;

    private Integer quantity;

}
