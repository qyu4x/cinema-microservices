package com.neko.orderservice.client;

import com.neko.orderservice.config.RestTemplateConfiguration;
import com.neko.orderservice.model.response.WebUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserServiceExternalClient {

    private final RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    public UserServiceExternalClient(RestTemplateConfiguration restTemplateConfiguration) {
        this.restTemplateConfiguration = restTemplateConfiguration;
    }

    public WebUserResponse findUserById(String userId) {
        return restTemplateConfiguration.restTemplate().getForObject("http://user-service/api/user/get/" + userId, WebUserResponse.class);
    }
}
