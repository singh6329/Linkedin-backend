package com.app.linkedin.posts_like_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic postLikedTopic()
    {
        return new NewTopic("post-liked-topic",3,(short)1);
    }

    @Bean
    NewTopic postDislikedTopic()
    {
        return new NewTopic("post-unliked-topic",3,(short)1);
    }
}
