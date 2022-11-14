package com.neko.moviescheduleservice.service.impl;

import com.neko.moviescheduleservice.model.entity.Schedule;
import com.neko.moviescheduleservice.model.request.ScheduleRequest;
import com.neko.moviescheduleservice.model.response.ScheduleResponse;
import com.neko.moviescheduleservice.repository.ScheduleRepository;
import com.neko.moviescheduleservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<ScheduleResponse> add(List<ScheduleRequest> scheduleRequest) {
        List<Schedule> schedules = new ArrayList<>();
        scheduleRequest.stream().forEach(
                request -> {
                    Schedule schedule = Schedule.builder()
                            .id(UUID.randomUUID().toString())
                            .showDate(request.getShowDate())
                            .startTime(request.getStartTime())
                            .endTime(request.getEndTime())
                            .price(request.getPrice())
                            .build();
                    schedules.add(schedule);
                }
        );

        scheduleRepository.saveAll(schedules);

        List<ScheduleResponse> scheduleResponses = new ArrayList<>();
        schedules.stream().forEach(
                response -> {
                    ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                            .id(UUID.randomUUID().toString())
                            .showDate(response.getShowDate())
                            .startTime(response.getStartTime())
                            .endTime(response.getEndTime())
                            .price(response.getPrice())
                            .build();
                    scheduleResponses.add(scheduleResponse);
                }
        );
        return scheduleResponses;
    }
}
