package com.neko.moviedataservice.client;

import com.neko.moviedataservice.model.response.WebMovieStudioResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class StudioExternalClientTest {

    @Autowired
    private StudioExternalClient studioExternalClient;

    @Test
    void testGetStudioMovieByMovieId() {
        WebMovieStudioResponse studioResponses = studioExternalClient.findMovieStudioByMovieId("test movie id");
        studioResponses.getData().stream().forEach(studioResponse -> {
            log.info("studio name is {} ", studioResponse.getStudio().getName());
        });
    }
}