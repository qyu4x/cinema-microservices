package com.neko.notificationservice.service;

;
import com.neko.notificationservice.model.request.PushNotificationRequest;

public interface PushNotificationService {

    void sendPushNotificationToToken(PushNotificationRequest pushNotificationRequest);

}
