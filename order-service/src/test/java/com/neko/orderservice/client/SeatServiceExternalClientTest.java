package com.neko.orderservice.client;

import com.neko.orderservice.model.response.WebSeatResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SeatServiceExternalClientTest {

    @Autowired
    private SeatServiceExternalClient seatServiceExternalClient;

    @Test
    void testCheckIfExist() {
        WebSeatResponse status = seatServiceExternalClient.findIfExist("A11");
        Assertions.assertEquals(true, status.getData());
    }
}