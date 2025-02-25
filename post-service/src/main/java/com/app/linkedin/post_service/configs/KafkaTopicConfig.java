package com.app.linkedin.post_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic postCreatedTopic()
    {
        return new NewTopic("post-created-topic",3,(short)1);
    }

    @Bean
    NewTopic postLikedTopic()
    {
        return new NewTopic("post-liked-topic",3,(short)1);
    }
}
