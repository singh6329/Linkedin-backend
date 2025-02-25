package com.app.linkedin.notification_service.consumer;

import com.app.linkedin.connections_service.events.AcceptConnectionRequestEvent;
import com.app.linkedin.connections_service.events.SendConnectionRequestEvent;
import com.app.linkedin.notification_service.entity.Notification;
import com.app.linkedin.notification_service.entity.enums.NotificationType;
import com.app.linkedin.notification_service.services.SendNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceConsumer {

    private final SendNotificationService sendNotificationService;

    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent)
    {
        String message = String.format("You have received the connection request from the user-id %d",sendConnectionRequestEvent.getSenderId());
        sendNotificationService.send(sendConnectionRequestEvent.getReceiverId(),message, NotificationType.CONNECTION);
    }

    @KafkaListener(topics = "accept-connection-request-topic")
    public void handleAcceptConnectionRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent)
    {
    String message = String.format("Your connection Request has been accepted by user-id %d",acceptConnectionRequestEvent.getReceiverId());
    sendNotificationService.send(acceptConnectionRequestEvent.getSenderId(),message,NotificationType.CONNECTION);
    }
}
