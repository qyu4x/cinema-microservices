package com.neko.seatservice.client;

import com.neko.seatservice.config.RestTemplateConfiguration;
import com.neko.seatservice.model.response.WebStudioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudioExternalClient {

    private final RestTemplateConfiguration restTemplate;

    @Autowired
    public StudioExternalClient(RestTemplateConfiguration restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WebStudioResponse findStudioById(String studioId) {
        return restTemplate.restTemplate().getForObject("http://studio-service/api/studio/find/" + studioId, WebStudioResponse.class);

    }

}
