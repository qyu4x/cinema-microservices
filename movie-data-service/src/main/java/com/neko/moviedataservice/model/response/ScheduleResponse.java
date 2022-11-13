package com.neko.moviedataservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
