package com.neko.moviedataservice.client;

import com.neko.moviedataservice.config.RestTemplateConfiguration;
import com.neko.moviedataservice.model.response.WebMovieStudioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudioExternalClient {

    private final RestTemplateConfiguration restTemplate;

    @Autowired
    public StudioExternalClient(RestTemplateConfiguration restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WebMovieStudioResponse findMovieStudioByMovieId(String movieId) {
        return restTemplate.restTemplate().getForObject("http://studio-service/api/studio/get/" + movieId, WebMovieStudioResponse.class);
    }

}
