package com.neko.moviedataservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebScheduleResponse {

    private Integer code;

    private String status;

    private List<ScheduleResponse> scheduleResponses;

}
