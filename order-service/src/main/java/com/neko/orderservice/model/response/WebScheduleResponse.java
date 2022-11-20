package com.neko.orderservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebScheduleResponse {

    private Integer code;

    private String status;

    private ScheduleResponse data;

}
