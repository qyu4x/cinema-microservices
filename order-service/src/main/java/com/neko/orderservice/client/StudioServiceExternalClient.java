package com.neko.orderservice.client;

import com.neko.orderservice.config.RestTemplateConfiguration;
import com.neko.orderservice.model.response.WebStudioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudioServiceExternalClient {

    private final RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    public StudioServiceExternalClient(RestTemplateConfiguration restTemplateConfiguration) {
        this.restTemplateConfiguration = restTemplateConfiguration;
    }


    public WebStudioResponse findStudioById(String studioId) {
        return restTemplateConfiguration.restTemplate().getForObject("http://studio-service/api/studio/find/" + studioId, WebStudioResponse.class);
    }
}
