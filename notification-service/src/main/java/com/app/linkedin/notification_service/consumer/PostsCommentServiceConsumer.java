package com.app.linkedin.notification_service.consumer;

import com.app.linkedin.notification_service.entity.enums.NotificationType;
import com.app.linkedin.notification_service.services.SendNotificationService;
import com.app.linkedin.posts_comment_service.events.PostsCommentedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsCommentServiceConsumer {
    private final SendNotificationService sendNotificationService;

    @KafkaListener(topics = "posts-commented-topic")
    public void handlePostsCommented(PostsCommentedEvent postsCommentedEvent)
    {
        if(!postsCommentedEvent.getCommentedById().equals(postsCommentedEvent.getCreatorId())) {
            log.info("Sending notification: handlePostsCommented {}", postsCommentedEvent);
            String message = String.format("User with id %d has been commented on your post with id %d", postsCommentedEvent.getCommentedById(), postsCommentedEvent.getPostId());
            sendNotificationService.send(postsCommentedEvent.getCreatorId(), message, NotificationType.POST);
        }
        log.info("Cannot send comment notification to user as user has commented on his own post!");
    }
}
