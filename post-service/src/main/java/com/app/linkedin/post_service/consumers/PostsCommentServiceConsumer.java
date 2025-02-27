package com.app.linkedin.post_service.consumers;

import com.app.linkedin.post_service.services.PostsService;
import com.app.linkedin.posts_comment_service.events.PostsCommentedEvent;
import com.app.linkedin.posts_comment_service.events.PostsUncommentedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostsCommentServiceConsumer {

    private final PostsService postsService;

    @KafkaListener(topics = "posts-commented-topic")
    void handlePostsCommentTopic(PostsCommentedEvent postsCommentedEvent)
    {
        log.info("Attempting to increment comment counter for postId {}",postsCommentedEvent.getPostId());
        postsService.increaseCommentsCounterOfPosts(postsCommentedEvent.getPostId());
        log.info("Comments counter updated success!");
    }

    @KafkaListener(topics = "posts-uncommented-topic")
    void handlePostsUnCommentedTopic(PostsUncommentedEvent postsUncommentedEvent)
    {
        log.info("Attempting to decrement comment counter for postId {}",postsUncommentedEvent.getPostId());
        postsService.decreaseCommentsCounterOfPosts(postsUncommentedEvent.getPostId());
        log.info("Comments counter update success!");
    }
}
