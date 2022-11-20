package com.neko.orderservice.client;

import com.neko.orderservice.config.RestTemplateConfiguration;
import com.neko.orderservice.model.response.WebSeatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeatServiceExternalClient {

    private final RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    public SeatServiceExternalClient(RestTemplateConfiguration restTemplateConfiguration) {
        this.restTemplateConfiguration = restTemplateConfiguration;
    }

    public WebSeatResponse findIfExist(String seatId) {
        return restTemplateConfiguration.restTemplate().getForObject("http://seat-service/api/seat/exists/" + seatId, WebSeatResponse.class);
    }
}
