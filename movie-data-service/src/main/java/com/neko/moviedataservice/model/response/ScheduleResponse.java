package com.neko.moviedataservice.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {

    private String id;

    private String movieId;

    private LocalDate showDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private BigDecimal price;


}
