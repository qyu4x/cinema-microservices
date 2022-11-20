package com.neko.notificationservice.service.impl;

import com.neko.notificationservice.firebase.FirebaseCloudMessagingService;
import com.neko.notificationservice.model.request.PushNotificationRequest;
import com.neko.notificationservice.service.PushNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushNotificationServiceImpl implements PushNotificationService {


    private final FirebaseCloudMessagingService firebaseCloudMessagingService;

    @Autowired
    public PushNotificationServiceImpl(FirebaseCloudMessagingService firebaseCloudMessagingService) {
        this.firebaseCloudMessagingService = firebaseCloudMessagingService;
    }


    @Override
    public void sendPushNotificationToToken(PushNotificationRequest pushNotificationRequest) {
        try {
            firebaseCloudMessagingService.sendMessageToToken(pushNotificationRequest);
        } catch (Exception exception) {
            log.error("Opps {}", exception.getMessage());
        }
    }
}
