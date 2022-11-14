package com.neko.moviedataservice.client;

import com.neko.moviedataservice.config.RestTemplateConfiguration;
import com.neko.moviedataservice.model.request.MovieScheduleRequest;
import com.neko.moviedataservice.model.request.ScheduleRequest;
import com.neko.moviedataservice.model.response.WebMovieScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleExternalClient {

    private final RestTemplateConfiguration restTemplate;

    @Autowired
    public ScheduleExternalClient(RestTemplateConfiguration restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WebMovieScheduleResponse postScheduleForMovies(List<MovieScheduleRequest> movieScheduleRequests) {
        return restTemplate.restTemplate().postForObject("http://movie-schedule-service/api/schedule/save", movieScheduleRequests, WebMovieScheduleResponse.class);
    }
}
