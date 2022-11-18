package com.neko.seatservice.client;

import com.neko.seatservice.model.response.WebStudioResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class StudioExternalClientTest {

    @Autowired
    private StudioExternalClient studioExternalClient;

    @Test
    void testGetStudioById() {

        WebStudioResponse studioResponse = studioExternalClient.findStudioById("1020eb99-ce42-46fc-bb75-f8782b6472d1");
        Assertions.assertNotNull(studioResponse);
        Assertions.assertEquals("studio sagiri chan", studioResponse.getData().getName());
    }
}