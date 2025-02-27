package com.app.linkedin.post_service.consumers;

import com.app.linkedin.post_service.services.PostsService;
import com.app.linkedin.posts_like_service.events.PostLikedEvent;
import com.app.linkedin.posts_like_service.events.PostUnlikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostsLikeServiceConsumer {

    private final PostsService postsService;

    @KafkaListener(topics = "post-liked-topic")
    void handlePostLikedTopic(PostLikedEvent postLikedEvent)
    {
        log.info("Attempting to increment like counter of post with id : {}",postLikedEvent.getPostId());
        postsService.increaseLikesCounterOfPosts(postLikedEvent.getPostId());
        log.info("Like count has been increased!");
    }

    @KafkaListener(topics = "post-unliked-topic")
    void handlePostUnliked(PostUnlikedEvent postUnlikedEvent)
    {
        log.info("Attempting to decrement like counter of post with id : {}",postUnlikedEvent.getPostId());
        postsService.decreaseLikesCounterOfPost(postUnlikedEvent.getPostId());
        log.info("Like count has been decreased!");
    }
}
