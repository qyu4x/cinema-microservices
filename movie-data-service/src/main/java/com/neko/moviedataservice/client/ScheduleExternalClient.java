package com.neko.moviedataservice.client;

import com.neko.moviedataservice.config.RestTemplateConfiguration;
import com.neko.moviedataservice.model.request.ScheduleRequest;
import com.neko.moviedataservice.model.response.WebScheduleResponse;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleExternalClient {

    private final RestTemplateConfiguration restTemplate;

    @Autowired
    public ScheduleExternalClient(RestTemplateConfiguration restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WebScheduleResponse postScheduleForMovies(List<ScheduleRequest> scheduleRequest) {
        return restTemplate.restTemplate().postForObject("http://movie-schedule-service/api/schedule/create", scheduleRequest, WebScheduleResponse.class);
    }
}
