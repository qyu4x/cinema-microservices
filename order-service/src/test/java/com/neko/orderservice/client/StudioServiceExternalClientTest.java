package com.neko.orderservice.client;

import com.neko.orderservice.model.response.WebStudioResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudioServiceExternalClientTest {

    @Autowired
    private StudioServiceExternalClient studioServiceExternalClient;

    @Test
    void testFindStudioById() {

        WebStudioResponse studioResponse = studioServiceExternalClient.findStudioById("e475e3dc-7c32-49a1-b5ee-cbe2440ee087");
        Assertions.assertEquals(200, studioResponse.getCode().intValue());

    }
}