package com.neko.moviescheduleservice.service.impl;

import com.neko.moviescheduleservice.model.request.ScheduleRequest;
import com.neko.moviescheduleservice.model.response.ScheduleResponse;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleServiceImplTest {

    @Autowired
    private ScheduleServiceImpl scheduleService;


    @Test
    void testAddSchedule() {
        List<ScheduleRequest> scheduleRequests = new ArrayList<>();
        ScheduleRequest scheduleRequest = new ScheduleRequest(
                UUID.randomUUID().toString(),
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now(),
                new BigDecimal(30000L)
        );
        scheduleRequests.add(scheduleRequest);
        List<ScheduleResponse> responses = scheduleService.add(scheduleRequests);
        Assertions.assertNotNull(responses);

    }
}