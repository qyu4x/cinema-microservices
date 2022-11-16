package com.neko.moviedataservice.client;

import com.neko.moviedataservice.model.response.WebMovieScheduleResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class ScheduleExternalClientTest {
    @Autowired
    private ScheduleExternalClient scheduleExternalClient;

    @Test
    void testFindMovieScheduleByMovieId() {
        WebMovieScheduleResponse movieScheduleResponses = scheduleExternalClient.findScheduleMovieByMovieId("test doang movie id");
        movieScheduleResponses.getData().forEach(movieScheduleResponse -> {
            log.info("id schedule {} ", movieScheduleResponse.getSchedule().getId());
        });
    }
}