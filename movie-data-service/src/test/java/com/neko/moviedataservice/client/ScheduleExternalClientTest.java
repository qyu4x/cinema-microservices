package com.neko.moviedataservice.client;

import com.neko.moviedataservice.model.request.ScheduleRequest;
import com.neko.moviedataservice.model.response.WebScheduleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleExternalClientTest {
    @Autowired
    private ScheduleExternalClient scheduleExternalClient;

    @Test
    void testPostSchedule() {

        List<ScheduleRequest> scheduleRequests = new ArrayList<>();
        ScheduleRequest scheduleRequest = new ScheduleRequest(
                UUID.randomUUID().toString(),
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now(),
                new BigDecimal(30000L)
        );
        scheduleRequests.add(scheduleRequest);

        WebScheduleResponse webScheduleResponse = scheduleExternalClient.postScheduleForMovies(scheduleRequests);
        Assertions.assertEquals(webScheduleResponse.getCode(), 200);
    }
}