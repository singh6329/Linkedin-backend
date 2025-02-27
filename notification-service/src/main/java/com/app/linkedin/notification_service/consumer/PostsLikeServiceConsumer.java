package com.app.linkedin.notification_service.consumer;

import com.app.linkedin.notification_service.clients.ConnectionClient;
import com.app.linkedin.notification_service.entity.enums.NotificationType;
import com.app.linkedin.notification_service.repositories.NotificationRepository;
import com.app.linkedin.notification_service.services.SendNotificationService;
import com.app.linkedin.posts_like_service.events.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsLikeServiceConsumer {

    private final SendNotificationService sendNotificationService;

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent)
    {
        if(!postLikedEvent.getLikedByUserId().equals(postLikedEvent.getCreatorId())) {
            log.info("Sending notifications: handlePostLikedEvent : {}", postLikedEvent);
            String message = String.format("Your post with id %d has been liked by user with id %d", postLikedEvent.getPostId(), postLikedEvent.getLikedByUserId());
            sendNotificationService.send(postLikedEvent.getCreatorId(), message, NotificationType.POST);
        }
        log.info("Cannot send like notification to user as user has liked his own post!");
    }
}
