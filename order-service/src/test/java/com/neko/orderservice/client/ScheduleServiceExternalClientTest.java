package com.neko.orderservice.client;

import com.neko.orderservice.model.response.WebScheduleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleServiceExternalClientTest {

    @Autowired
    private ScheduleServiceExternalClient scheduleServiceExternalClient;


    @Test
    void testFindScheduleById() {

        WebScheduleResponse scheduleResponse = scheduleServiceExternalClient.findScheduleById("fc5b1366-d0ab-4b55-b6c5-6d9911397781");
        Assertions.assertEquals(200, scheduleResponse.getCode().intValue());

    }
}