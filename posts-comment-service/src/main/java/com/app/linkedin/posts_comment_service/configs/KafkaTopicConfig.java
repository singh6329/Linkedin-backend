package com.app.linkedin.posts_comment_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic postsCommentedTopic()
    {
        return new NewTopic("posts-commented-topic",3,(short)1);
    }

    @Bean
    NewTopic postsUncommentedTopic()
    {
        return new NewTopic("posts-uncommented-topic",3,(short)1);
    }
}
