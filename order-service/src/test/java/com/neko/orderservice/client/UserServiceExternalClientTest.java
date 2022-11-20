package com.neko.orderservice.client;

import com.neko.orderservice.model.response.WebUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceExternalClientTest {

    @Autowired
    private UserServiceExternalClient userServiceExternalClient;

    @Test
    void testGetUserByUserId() {

        WebUserResponse userResponse = userServiceExternalClient.findUserById("ba4c549c-db30-4905-aae5-6a9c8024e188");
        Assertions.assertEquals(200, userResponse.getCode().intValue());

    }
}