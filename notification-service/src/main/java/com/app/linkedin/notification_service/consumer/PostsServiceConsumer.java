package com.app.linkedin.notification_service.consumer;

import com.app.linkedin.notification_service.clients.ConnectionClient;
import com.app.linkedin.notification_service.dtos.PersonDto;
import com.app.linkedin.notification_service.entity.enums.NotificationType;
import com.app.linkedin.notification_service.repositories.NotificationRepository;
import com.app.linkedin.notification_service.services.SendNotificationService;
import com.app.linkedin.post_service.events.PostCreatedEvent;
import com.app.linkedin.post_service.events.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsServiceConsumer {

    private final ConnectionClient connectionClient;
    private final NotificationRepository notificationRepository;
    private final SendNotificationService sendNotificationService;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent)
    {
        log.info("Sending notifications: handlePostsCreated : {}",postCreatedEvent);
        List<PersonDto> connections = connectionClient.getFirstDegreeConnectionsOfUser(postCreatedEvent.getCreatorId());
        String message = "Your connection "+postCreatedEvent.getCreatorId()+" has created a post! Check it out...";
        for (PersonDto connection:connections)
        {
            sendNotificationService.send(connection.getUserId(),message,NotificationType.POST);
        }
    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent)
    {
        log.info("Sending notifications: handlePostLikedEvent : {}",postLikedEvent);
        String message = String.format("Your post, %d has been liked by %d",postLikedEvent.getPostId(),postLikedEvent.getLikedByUserId());
        sendNotificationService.send(postLikedEvent.getCreatorId(), message, NotificationType.POST);
    }


}
