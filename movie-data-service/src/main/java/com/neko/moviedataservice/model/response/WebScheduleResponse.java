package com.neko.moviedataservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebScheduleResponse {

    private Integer code;

    private String status;

    private List<ScheduleResponse> data;

}
