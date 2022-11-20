package com.neko.moviescheduleservice.service;

import com.neko.moviescheduleservice.model.request.ScheduleRequest;
import com.neko.moviescheduleservice.model.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {

    List<ScheduleResponse> add(List<ScheduleRequest> scheduleRequest);

    ScheduleResponse findScheduleById(String scheduleId);

}
