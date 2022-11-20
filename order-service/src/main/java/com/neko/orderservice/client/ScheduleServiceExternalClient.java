package com.neko.orderservice.client;

import com.neko.orderservice.config.RestTemplateConfiguration;
import com.neko.orderservice.model.response.WebScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleServiceExternalClient {

    private final RestTemplateConfiguration restTemplateConfiguration;


    @Autowired
    public ScheduleServiceExternalClient(RestTemplateConfiguration restTemplateConfiguration) {
        this.restTemplateConfiguration = restTemplateConfiguration;
    }

    public WebScheduleResponse findScheduleById(String scheduleId) {
        return restTemplateConfiguration.restTemplate().getForObject("http://movie-schedule-service/api/schedule/get-schedule/" + scheduleId, WebScheduleResponse.class);
    }
}
